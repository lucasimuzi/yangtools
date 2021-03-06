/*
 * Copyright (c) 2014 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.yangtools.yang.data.api.schema;

import java.util.Collection;
import org.opendaylight.yangtools.yang.data.api.YangInstanceIdentifier;
import org.opendaylight.yangtools.yang.data.api.YangInstanceIdentifier.AugmentationIdentifier;
import org.opendaylight.yangtools.yang.data.api.YangInstanceIdentifier.PathArgument;
import org.opendaylight.yangtools.yang.model.api.AugmentationSchema;

/**
 * Data instance of <code>augment</code> assiociated with parent node.
 *
 * Augmentation is addition of subtree defined by other external YANG Model and
 * is schema for subtree is described by instance of  {@link AugmentationSchema}
 * associated with parent node of this node.
 *
 * Augmentation node MUST NOT be direct child of other augmentation node.
 *
 */
public interface AugmentationNode extends MixinNode, DataContainerNode<AugmentationIdentifier>,
    DataContainerChild<YangInstanceIdentifier.AugmentationIdentifier, Collection<DataContainerChild<? extends PathArgument, ?>>> {

    /**
     * Gets identifier of augmentation node
     *
     * Returned identifier of augmentation node contains all possible
     * direct child QNames.
     *
     * This is sufficient to identify instance of augmentation,
     * since RFC6020 states that <code>augment</code> that augment
     * statement must not add multiple nodes from same namespace
     * / module
     * to the target node.
     *
     * @return Identifier which uniquelly identifies augmentation in particular subtree.
     *
     */
    @Override
    AugmentationIdentifier getIdentifier();
}
