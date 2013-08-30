package com.qcadoo.security.internal.hooks;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.security.api.SecurityService;

public class UserModelHooksTest {

    private UserModelHooks userModelHooks;

    @Mock
    private SecurityService securityService;

    @Mock
    private DataDefinition userDD;

    @Mock
    private Entity user;

    @Before
    public final void init() {
        MockitoAnnotations.initMocks(this);

        userModelHooks = new UserModelHooks();

        ReflectionTestUtils.setField(userModelHooks, "securityService", securityService);
    }

    @Test
    public final void shouldAllowToDeleteUserIfCurrentUserIdIsOtherThanInDeletingOne() {
        // given
        stubCurrentUserId(1L);
        stubUserEntityId(9999L);

        // when
        boolean canDelete = userModelHooks.preventSelfDeletion(userDD, user);

        // then
        assertTrue(canDelete);
    }

    @Test
    public final void shouldAllowToDeleteUserIfCurrentUserIdIsNull() {
        // given
        stubCurrentUserId(null);
        stubUserEntityId(9999L);

        // when
        boolean canDelete = userModelHooks.preventSelfDeletion(userDD, user);

        // then
        assertTrue(canDelete);
    }

    @Test
    public final void shouldDenyUserDeletionIfCurrentUserIdIsTheSameAsIdInDeletingOne() {
        // given
        Long id = 1L;
        stubCurrentUserId(id);
        stubUserEntityId(id);

        // when
        boolean canDelete = userModelHooks.preventSelfDeletion(userDD, user);

        // then
        assertFalse(canDelete);
    }

    private void stubCurrentUserId(final Long id) {
        given(securityService.getCurrentUserId()).willReturn(id);
    }

    private void stubUserEntityId(final Long id) {
        given(user.getId()).willReturn(id);
    }

}
