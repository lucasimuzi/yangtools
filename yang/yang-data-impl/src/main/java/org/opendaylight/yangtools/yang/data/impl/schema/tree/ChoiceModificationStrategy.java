/*
 * Copyright (c) 2014 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.yangtools.yang.data.impl.schema.tree;

import static com.google.common.base.Preconditions.checkArgument;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.opendaylight.yangtools.yang.data.api.YangInstanceIdentifier;
import org.opendaylight.yangtools.yang.data.api.schema.ChoiceNode;
import org.opendaylight.yangtools.yang.data.api.schema.NormalizedNode;
import org.opendaylight.yangtools.yang.data.impl.schema.builder.api.DataContainerNodeBuilder;
import org.opendaylight.yangtools.yang.data.impl.schema.builder.impl.ImmutableChoiceNodeBuilder;
import org.opendaylight.yangtools.yang.model.api.ChoiceCaseNode;
import org.opendaylight.yangtools.yang.model.api.ChoiceSchemaNode;
import org.opendaylight.yangtools.yang.model.api.DataSchemaNode;

final class ChoiceModificationStrategy extends AbstractNodeContainerModificationStrategy {
    private final Map<YangInstanceIdentifier.PathArgument, ModificationApplyOperation> childNodes;

    ChoiceModificationStrategy(final ChoiceSchemaNode schemaNode) {
        super(ChoiceNode.class);
        ImmutableMap.Builder<YangInstanceIdentifier.PathArgument, ModificationApplyOperation> child = ImmutableMap.builder();

        for (ChoiceCaseNode caze : schemaNode.getCases()) {
            for (DataSchemaNode cazeChild : caze.getChildNodes()) {
                SchemaAwareApplyOperation childNode = SchemaAwareApplyOperation.from(cazeChild);
                child.put(new YangInstanceIdentifier.NodeIdentifier(cazeChild.getQName()), childNode);
            }
        }
        childNodes = child.build();
    }

    @Override
    public Optional<ModificationApplyOperation> getChild(final YangInstanceIdentifier.PathArgument child) {
        return Optional.fromNullable(childNodes.get(child));
    }

    @Override
    @SuppressWarnings("rawtypes")
    protected DataContainerNodeBuilder createBuilder(final NormalizedNode<?, ?> original) {
        checkArgument(original instanceof ChoiceNode);
        return ImmutableChoiceNodeBuilder.create((ChoiceNode) original);
    }
}