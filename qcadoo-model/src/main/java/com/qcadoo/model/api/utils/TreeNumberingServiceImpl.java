/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.9
 *
 * This file is part of Qcadoo.
 *
 * Qcadoo is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * ***************************************************************************
 */
package com.qcadoo.model.api.utils;

import static com.google.common.collect.Lists.newLinkedList;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.EntityTree;
import com.qcadoo.model.api.EntityTreeNode;
import com.qcadoo.model.api.types.TreeType;
import com.qcadoo.model.internal.EntityTreeImpl;
import com.qcadoo.model.internal.api.PriorityService;

@Service
public class TreeNumberingServiceImpl implements TreeNumberingService {
    
    @Autowired
    private PriorityService priorityService;
    
    @Override
    public void generateTreeNumbers(final EntityTree tree) {
        if(tree.getRoot() == null) {
            return;
        }
        assignNumberToTreeNode(tree.getRoot(), Lists.newLinkedList(Lists.newArrayList("1")));
    }
    
    @Override
    public void generateTreeNumbers(final EntityTreeNode treeNode) {
        assignNumberToTreeNode(treeNode, Lists.newLinkedList(Lists.newArrayList("1")));
    }
    
    @Override
    public void generateNumbersAndUpdateTree(final DataDefinition dd, final String joinFieldName, final Long belongsToEntityId) {
        EntityTree tree = new EntityTreeImpl(dd, joinFieldName, belongsToEntityId);
        if (tree.getRoot() == null || tree.getRoot().getField(TreeType.NODE_NUMBER_FIELD) != null) {
            return;
        }
        generateTreeNumbers(tree);
        for (Entity treeNode : tree) {
            dd.save(treeNode);
        }
    }

    @Override
    public Comparator<Entity> getTreeNodesNumberComparator() {
        return new TreeNodesNumberComparator();
    }
    
    void assignNumberToTreeNode(final EntityTreeNode treeNode, final Deque<String> chain) {
        treeNode.setField(TreeType.NODE_NUMBER_FIELD, collectionToString(chain));
        
        List<EntityTreeNode> childrens = newLinkedList(treeNode.getChildren());
        Collections.sort(childrens, priorityService.getEntityPriorityComparator());
        
        int charNumber = 0;
        for (EntityTreeNode child : childrens) {
            Deque<String> newBranch = Lists.newLinkedList(chain);
            if (childrens.size() == 1) {
                incrementLastChainNumber(newBranch);
            } else {
                incrementLastChainCharacter(newBranch, charNumber++);
            }
            assignNumberToTreeNode(child, newBranch);
        }
        
    }

    private void incrementLastChainNumber(final Deque<String> chain) {
        Integer nextNumber = Integer.valueOf(chain.pollLast()) + 1;
        chain.addLast(nextNumber.toString());
    }
    
    private void incrementLastChainCharacter(final Deque<String> chain, final int charNumber) {
        chain.addLast(String.valueOf((char) (65 + charNumber)));
        chain.addLast("1");
    }
    
    private String collectionToString(final Collection<String> collection) {
        return StringUtils.join(collection, '.') + '.';
    }
    
    private final class TreeNodesNumberComparator implements Comparator<Entity> {
        @Override
        public int compare(final Entity e1, final Entity e2) {
            String n1 = e1.getStringField(TreeType.NODE_NUMBER_FIELD);
            String n2 = e2.getStringField(TreeType.NODE_NUMBER_FIELD);
            return n1.compareTo(n2);
        }
    }
}
