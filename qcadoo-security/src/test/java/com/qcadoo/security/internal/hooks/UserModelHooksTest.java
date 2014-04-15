package com.qcadoo.security.internal.hooks;

import static com.qcadoo.testing.model.EntityTestUtils.stubStringField;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.security.api.SecurityService;
import com.qcadoo.security.constants.UserFields;

public class UserModelHooksTest {

    private static final String TEST_USER_NAME = "testUserName";

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

    @Test
    public final void shouldNotReplaceAnyNameWithUserName() {
        // given
        stubUserNames(TEST_USER_NAME, "someFirstName", "someLastName");

        // when
        userModelHooks.setDefaultNames(userDD, user);

        // then
        verify(user, never()).setField(UserFields.FIRST_NAME, TEST_USER_NAME);
        verify(user, never()).setField(UserFields.LAST_NAME, TEST_USER_NAME);
    }

    @Test
    public final void shouldReplaceBlankLastNameWithUserName() {
        // given
        stubUserNames(TEST_USER_NAME, "someFirstName", null);

        // when
        userModelHooks.setDefaultNames(userDD, user);

        // then
        verify(user, never()).setField(UserFields.FIRST_NAME, TEST_USER_NAME);
        verify(user).setField(UserFields.LAST_NAME, TEST_USER_NAME);
    }

    @Test
    public final void shouldReplaceNullLastNameWithUserName() {
        // given
        stubUserNames(TEST_USER_NAME, "someFirstName", null);

        // when
        userModelHooks.setDefaultNames(userDD, user);

        // then
        verify(user, never()).setField(UserFields.FIRST_NAME, TEST_USER_NAME);
        verify(user).setField(UserFields.LAST_NAME, TEST_USER_NAME);
    }

    @Test
    public final void shouldReplaceBlankFirstNameWithUserName() {
        // given
        stubUserNames(TEST_USER_NAME, "  ", "someLastName");

        // when
        userModelHooks.setDefaultNames(userDD, user);

        // then
        verify(user).setField(UserFields.FIRST_NAME, TEST_USER_NAME);
        verify(user, never()).setField(UserFields.LAST_NAME, TEST_USER_NAME);
    }

    @Test
    public final void shouldReplaceNullFirstNameWithUserName() {
        // given
        stubUserNames(TEST_USER_NAME, null, "someLastName");

        // when
        userModelHooks.setDefaultNames(userDD, user);

        // then
        verify(user).setField(UserFields.FIRST_NAME, TEST_USER_NAME);
        verify(user, never()).setField(UserFields.LAST_NAME, TEST_USER_NAME);
    }

    @Test
    public final void shouldReplaceBothBlankFirstAndLastNameWithUserName() {
        // given
        stubUserNames(TEST_USER_NAME, " ", "   ");

        // when
        userModelHooks.setDefaultNames(userDD, user);

        // then
        verify(user).setField(UserFields.FIRST_NAME, TEST_USER_NAME);
        verify(user).setField(UserFields.LAST_NAME, TEST_USER_NAME);
    }

    @Test
    public final void shouldReplaceBothNullFirstAndLastNameWithUserName() {
        // given
        stubUserNames(TEST_USER_NAME, null, null);

        // when
        userModelHooks.setDefaultNames(userDD, user);

        // then
        verify(user).setField(UserFields.FIRST_NAME, TEST_USER_NAME);
        verify(user).setField(UserFields.LAST_NAME, TEST_USER_NAME);
    }

    private void stubUserNames(final String userName, final String firstName, final String lastName) {
        stubStringField(user, UserFields.USER_NAME, userName);
        stubStringField(user, UserFields.FIRST_NAME, firstName);
        stubStringField(user, UserFields.LAST_NAME, lastName);
    }

    private void stubCurrentUserId(final Long id) {
        given(securityService.getCurrentUserId()).willReturn(id);
    }

    private void stubUserEntityId(final Long id) {
        given(user.getId()).willReturn(id);
    }

}
