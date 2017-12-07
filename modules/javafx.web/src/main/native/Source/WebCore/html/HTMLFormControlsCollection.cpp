/*
 * Copyright (C) 1999 Lars Knoll (knoll@kde.org)
 *           (C) 1999 Antti Koivisto (koivisto@kde.org)
 * Copyright (C) 2003, 2004, 2005, 2006, 2007, 2010, 2011, 2012 Apple Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library General Public License
 * along with this library; see the file COPYING.LIB.  If not, write to
 * the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA 02110-1301, USA.
 *
 */

#include "config.h"
#include "HTMLFormControlsCollection.h"

#include "HTMLFieldSetElement.h"
#include "HTMLFormElement.h"
#include "HTMLImageElement.h"
#include "HTMLNames.h"

namespace WebCore {

using namespace HTMLNames;

// Since the collections are to be "live", we have to do the
// calculation every time if anything has changed.

HTMLFormControlsCollection::HTMLFormControlsCollection(ContainerNode& ownerNode)
    : CachedHTMLCollection<HTMLFormControlsCollection, CollectionTypeTraits<FormControls>::traversalType>(ownerNode, FormControls)
    , m_cachedElement(nullptr)
    , m_cachedElementOffsetInArray(0)
{
    ASSERT(is<HTMLFormElement>(ownerNode) || is<HTMLFieldSetElement>(ownerNode));
}

Ref<HTMLFormControlsCollection> HTMLFormControlsCollection::create(ContainerNode& ownerNode, CollectionType)
{
    return adoptRef(*new HTMLFormControlsCollection(ownerNode));
}

HTMLFormControlsCollection::~HTMLFormControlsCollection()
{
}

std::optional<Variant<RefPtr<RadioNodeList>, RefPtr<Element>>> HTMLFormControlsCollection::namedItemOrItems(const String& name) const
{
    auto namedItems = this->namedItems(name);

    if (namedItems.isEmpty())
        return std::nullopt;
    if (namedItems.size() == 1)
        return Variant<RefPtr<RadioNodeList>, RefPtr<Element>> { RefPtr<Element> { WTFMove(namedItems[0]) } };

    return Variant<RefPtr<RadioNodeList>, RefPtr<Element>> { RefPtr<RadioNodeList> { ownerNode().radioNodeList(name) } };
}

const Vector<FormAssociatedElement*>& HTMLFormControlsCollection::formControlElements() const
{
    ASSERT(is<HTMLFormElement>(ownerNode()) || is<HTMLFieldSetElement>(ownerNode()));
    if (is<HTMLFormElement>(ownerNode()))
        return downcast<HTMLFormElement>(ownerNode()).associatedElements();
    return downcast<HTMLFieldSetElement>(ownerNode()).associatedElements();
}

const Vector<HTMLImageElement*>& HTMLFormControlsCollection::formImageElements() const
{
    ASSERT(is<HTMLFormElement>(ownerNode()));
    return downcast<HTMLFormElement>(ownerNode()).imageElements();
}

static unsigned findFormAssociatedElement(const Vector<FormAssociatedElement*>& elements, const Element& element)
{
    for (unsigned i = 0; i < elements.size(); ++i) {
        auto& associatedElement = *elements[i];
        if (associatedElement.isEnumeratable() && &associatedElement.asHTMLElement() == &element)
            return i;
    }
    return elements.size();
}

HTMLElement* HTMLFormControlsCollection::customElementAfter(Element* current) const
{
    const Vector<FormAssociatedElement*>& elements = formControlElements();
    unsigned start;
    if (!current)
        start = 0;
    else if (m_cachedElement == current)
        start = m_cachedElementOffsetInArray + 1;
    else
        start = findFormAssociatedElement(elements, *current) + 1;

    for (unsigned i = start; i < elements.size(); ++i) {
        FormAssociatedElement& element = *elements[i];
        if (element.isEnumeratable()) {
            m_cachedElement = &element.asHTMLElement();
            m_cachedElementOffsetInArray = i;
            return &element.asHTMLElement();
        }
    }
    return nullptr;
}

void HTMLFormControlsCollection::updateNamedElementCache() const
{
    if (hasNamedElementCache())
        return;

    auto cache = std::make_unique<CollectionNamedElementCache>();

    bool ownerIsFormElement = is<HTMLFormElement>(ownerNode());
    HashSet<AtomicStringImpl*> foundInputElements;

    for (auto& elementPtr : formControlElements()) {
        FormAssociatedElement& associatedElement = *elementPtr;
        if (associatedElement.isEnumeratable()) {
            HTMLElement& element = associatedElement.asHTMLElement();
            const AtomicString& id = element.getIdAttribute();
            if (!id.isEmpty()) {
                cache->appendToIdCache(id, element);
                if (ownerIsFormElement)
                    foundInputElements.add(id.impl());
            }
            const AtomicString& name = element.getNameAttribute();
            if (!name.isEmpty() && id != name) {
                cache->appendToNameCache(name, element);
                if (ownerIsFormElement)
                    foundInputElements.add(name.impl());
            }
        }
    }
    if (ownerIsFormElement) {
        for (auto* elementPtr : formImageElements()) {
            HTMLImageElement& element = *elementPtr;
            const AtomicString& id = element.getIdAttribute();
            if (!id.isEmpty() && !foundInputElements.contains(id.impl()))
                cache->appendToIdCache(id, element);
            const AtomicString& name = element.getNameAttribute();
            if (!name.isEmpty() && id != name && !foundInputElements.contains(name.impl()))
                cache->appendToNameCache(name, element);
        }
    }

    setNamedItemCache(WTFMove(cache));
}

void HTMLFormControlsCollection::invalidateCacheForDocument(Document& document)
{
    CachedHTMLCollection<HTMLFormControlsCollection, CollectionTypeTraits<FormControls>::traversalType>::invalidateCacheForDocument(document);
    m_cachedElement = nullptr;
    m_cachedElementOffsetInArray = 0;
}

}
