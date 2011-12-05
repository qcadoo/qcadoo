package com.qcadoo.model.api.utils;

import static com.google.common.base.Preconditions.checkArgument;

import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.EntityTreeNode;
import com.qcadoo.model.internal.EntityTreeNodeImpl;

public final class EntityTreeUtils {

    private EntityTreeUtils() {
    }

    public static EntityTreeNode getEntityTreeNode(final Entity entity, final EntityTreeNode parentNode) {
        int priority = 1;
        if (parentNode != null) {
            priority = parentNode.getChildren().size() + 1;
        }
        return getEntityTreeNode(entity, parentNode, priority);
    }

    public static EntityTreeNode getEntityTreeNode(final Entity entity, final EntityTreeNode parentNode, final int priority) {
        checkArgument(entity != null, "given entity should not be null!");

        EntityTreeNode node = new EntityTreeNodeImpl(entity);
        node.setField("priority", priority);
        if (parentNode != null) {
            node.setField("parent", parentNode);
            ((EntityTreeNodeImpl) parentNode).addChild(node);
        }
        return node;
    }

}
