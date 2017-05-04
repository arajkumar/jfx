/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package myapp3;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import myapp3.pkg3.MyData;

import static myapp3.Constants.*;

// This logic is copied from AppTableViewUnexported.

/**
 * Modular test application for testing JavaFX beans.
 * This is launched by ModuleLauncherTest.
 */
public class AppTableViewQualExported extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Application.launch(args);
        } catch (Throwable t) {
            System.err.println("ERROR: caught unexpected exception: " + t);
            t.printStackTrace(System.err);
            System.exit(ERROR_UNEXPECTED_EXCEPTION);
        }
    }

    private Logger logger;
    private Handler logHandler;
    private final List<Throwable> errs = new ArrayList<>();

    private void initLogger() {
        Locale.setDefault(Locale.US);

        // Initialize Logger
        logHandler = new Handler() {
            @Override
            public void publish(LogRecord record) {
                final Throwable t = record.getThrown();
                // detect any Throwable:
                if (t != null) {
                    errs.add(t);
                }
            }

            @Override
            public void flush() {
            }

            @Override
            public void close() {
            }
        };

        logger = Logger.getLogger("javafx.scene.control");
        logger.addHandler(logHandler);
    }

    @Override
    public void start(Stage stage) throws Exception {
        initLogger();

        try {
            StackPane root = new StackPane();
            Scene scene = new Scene(root);
            TableView<MyData> tableView = new TableView<>();

            // Name column
            TableColumn<MyData, String> nameCol = new TableColumn<>();
            nameCol.setText("Name");
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

            // Value column
            TableColumn<MyData, Integer> valueCol = new TableColumn<>();
            valueCol.setText("Value");
            valueCol.setCellValueFactory(new PropertyValueFactory<>("value"));

            tableView.getColumns().addAll(nameCol, valueCol);

            tableView.getItems().add(new MyData("Row A", 1));
            tableView.getItems().add(new MyData("Row B", 2));
            tableView.getItems().add(new MyData("Row C", 3));

            root.getChildren().add(tableView);

            stage.setScene(scene);
            System.err.println("The following two WARNING messages are expected:");
            stage.show();

            // Hide the stage after the specified amount of time
            KeyFrame kf = new KeyFrame(Duration.millis(SHOWTIME), e -> stage.hide());
            Timeline timeline = new Timeline(kf);
            timeline.play();
        } catch (Error | Exception ex) {
            System.err.println("ERROR: caught unexpected exception: " + ex);
            ex.printStackTrace(System.err);
            System.exit(ERROR_UNEXPECTED_EXCEPTION);
        }
    }

    private void fail(String message, Throwable t) {
        if (message != null) {
            System.err.print(message + ": ");
        }
        if (t != null) {
            System.err.println(t);
            t.printStackTrace();
        } else {
            System.err.println();
        }
        System.exit(ERROR_ASSERTION_FAILURE);
    }

    @Override public void stop() {
        final int expectedExceptions = 2; // One for each PropertyValueFactory

        if (errs.isEmpty()) {
            fail("ERROR: did not get the expected exception", null);
        }

        if (expectedExceptions != errs.size()) {
            fail("ERROR: expected " + expectedExceptions + " exceptions, got: " + errs.size(), null);
        }

        for (Throwable t : errs) {
            if (! (t instanceof RuntimeException)) {
                fail("ERROR: unexpected exception: ", t);
            }

            RuntimeException ex = (RuntimeException) t;
            Throwable cause = ex.getCause();
            if (! (cause instanceof IllegalAccessException)) {
                fail("ERROR: unexpected cause: ", ex);
            }

            String message = cause.getMessage();
            if (message == null) {
                fail("ERROR: detail message of cause is null", ex);
            }

            boolean badMessage = false;
            if (!message.contains(" cannot access class ")) badMessage = true;
            if (!message.contains(" does not open ")) badMessage = true;
            if (!message.endsWith(" to javafx.base")) badMessage = true;
            if (badMessage) {
                fail("ERROR: detail message not formatted correctly", ex);
            }
        }

        // We got the expected exception, exit normally
        System.exit(ERROR_NONE);
    }

}
