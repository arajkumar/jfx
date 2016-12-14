/*
 * Copyright (C) 2014-2015 Apple Inc. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY APPLE INC. AND ITS CONTRIBUTORS ``AS IS''
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL APPLE INC. OR ITS CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

#ifndef DataDetectorsCoreSPI_h
#define DataDetectorsCoreSPI_h

#if USE(APPLE_INTERNAL_SDK)

#import <DataDetectorsCore/DDBinderKeys_Private.h>
#import <DataDetectorsCore/DDScannerResult.h>
#import <DataDetectorsCore/DataDetectorsCore.h>
#if PLATFORM(IOS)
#import <DataDetectorsCore/DDURLifier.h>
#endif
#else // !USE(APPLE_INTERNAL_SDK)

typedef enum {
    DDScannerTypeStandard = 0,
    DDScannerType1 = 1,
    DDScannerType2 = 2,
} DDScannerType;

enum {
    DDScannerCopyResultsOptionsNone = 0,
    DDScannerCopyResultsOptionsNoOverlap = 1 << 0,
    DDScannerCopyResultsOptionsCoalesceSignatures = 1 << 1,
};

enum {
    DDURLifierPhoneNumberDetectionNone = 0,
    DDURLifierPhoneNumberDetectionRegular = 1 << 1,
    DDURLifierPhoneNumberDetectionQuotedShorts = 1 << 2,
    DDURLifierPhoneNumberDetectionUnquotedShorts = 1 << 3
};
typedef NSUInteger DDURLifierPhoneNumberDetectionTypes;

typedef enum __DDTextCoalescingType {
    DDTextCoalescingTypeNone = 0,
    DDTextCoalescingTypeSpace = 1,
    DDTextCoalescingTypeTab = 2,
    DDTextCoalescingTypeLineBreak = 3,
    DDTextCoalescingTypeHardBreak = 4,
} DDTextCoalescingType;

typedef enum {
    DDResultCategoryUnknown = 0,
    DDResultCategoryLink = 1,
    DDResultCategoryPhoneNumber = 2,
    DDResultCategoryAddress = 3,
    DDResultCategoryCalendarEvent = 4,
    DDResultCategoryMisc = 5,
} DDResultCategory;

typedef enum __DDTextFragmentType {
    DDTextFragmentTypeTrimWhiteSpace =  0x1,
    DDTextFragmentTypeIgnoreCRLF =  0x2,
} DDTextFragmentMode;

extern CFStringRef const DDBinderHttpURLKey;
extern CFStringRef const DDBinderWebURLKey;
extern CFStringRef const DDBinderMailURLKey;
extern CFStringRef const DDBinderGenericURLKey;
extern CFStringRef const DDBinderEmailKey;
extern CFStringRef const DDBinderTrackingNumberKey;
extern CFStringRef const DDBinderFlightInformationKey;
extern CFStringRef const DDBinderSignatureBlockKey;
extern NSString * const DDURLScheme;

@interface DDScannerResult : NSObject <NSCoding, NSSecureCoding>
+ (NSArray *)resultsFromCoreResults:(CFArrayRef)coreResults;
@end

#define DDResultPropertyPassiveDisplay   (1 << 0)

typedef struct __DDQueryOffset {
    CFIndex queryIndex;
    CFIndex offset;
} DDQueryOffset;

typedef struct __DDQueryRange {
    DDQueryOffset start;
    DDQueryOffset end;
} DDQueryRange;

#endif // !USE(APPLE_INTERNAL_SDK)

typedef struct __DDResult *DDResultRef;
typedef struct __DDScanQuery *DDScanQueryRef;
typedef struct __DDScanner *DDScannerRef;

typedef CFIndex DDScannerCopyResultsOptions;
typedef CFIndex DDScannerOptions;

extern "C" {

extern const DDScannerCopyResultsOptions DDScannerCopyResultsOptionsForPassiveUse;

DDScannerRef DDScannerCreate(DDScannerType, DDScannerOptions, CFErrorRef*);
DDScanQueryRef DDScanQueryCreate(CFAllocatorRef);
DDScanQueryRef DDScanQueryCreateFromString(CFAllocatorRef, CFStringRef, CFRange);
Boolean DDScannerScanQuery(DDScannerRef, DDScanQueryRef);
CFArrayRef DDScannerCopyResultsWithOptions(DDScannerRef, DDScannerCopyResultsOptions);
CFRange DDResultGetRange(DDResultRef);
CFStringRef DDResultGetType(DDResultRef);
DDResultCategory DDResultGetCategory(DDResultRef);
Boolean DDResultIsPastDate(DDResultRef, CFDateRef referenceDate, CFTimeZoneRef referenceTimeZone);
void DDScanQueryAddTextFragment(DDScanQueryRef, CFStringRef, CFRange, void *identifier, DDTextFragmentMode, DDTextCoalescingType);
void DDScanQueryAddSeparator(DDScanQueryRef, DDTextCoalescingType);
void DDScanQueryAddLineBreak(DDScanQueryRef);
void *DDScanQueryGetFragmentMetaData(DDScanQueryRef, CFIndex queryIndex);
bool DDResultHasProperties(DDResultRef, CFIndex propertySet);
CFArrayRef DDResultGetSubResults(DDResultRef);
DDQueryRange DDResultGetQueryRangeForURLification(DDResultRef);

}

#endif // DataDetectorsCoreSPI_h
