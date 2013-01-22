package com.qcadoo.security.internal.validators;

import static com.qcadoo.security.constants.QcadooSecurityConstants.ROLE_ADMIN;
import static com.qcadoo.security.constants.QcadooSecurityConstants.ROLE_SUPERADMIN;
import static com.qcadoo.security.constants.QcadooSecurityConstants.ROLE_SUPERVISOR;
import static com.qcadoo.security.constants.QcadooSecurityConstants.ROLE_USER;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.FieldDefinition;
import com.qcadoo.security.api.SecurityService;
import com.qcadoo.security.constants.UserFields;

public class UserRoleValidationServiceTest {

    private UserRoleValidationService userRoleValidationService;

    @Mock
    private SecurityService securityService;

    @Mock
    private DataDefinition userDataDefMock;

    @Mock
    private FieldDefinition userRoleFieldDefMock;

    @Mock
    private Entity userEntityMock, currentUserEntityMock, existingUserEntityMock;

    @Mock
    private SecurityContext securityContext;

    @Before
    public final void init() {
        MockitoAnnotations.initMocks(this);

        given(securityService.getCurrentUserId()).willReturn(1L);

        given(userDataDefMock.getField(UserFields.ROLE)).willReturn(userRoleFieldDefMock);
        given(userDataDefMock.get(1L)).willReturn(currentUserEntityMock);

        given(userEntityMock.getId()).willReturn(1000L);
        given(userDataDefMock.get(1000L)).willReturn(existingUserEntityMock);

        SecurityContextHolder.setContext(securityContext);

        userRoleValidationService = new UserRoleValidationService();
        ReflectionTestUtils.setField(userRoleValidationService, "securityService", securityService);
    }

    private void stubRoleTransition(final String from, final String to) {
        given(existingUserEntityMock.getStringField(UserFields.ROLE)).willReturn(from);
        given(existingUserEntityMock.getField(UserFields.ROLE)).willReturn(from);

        given(userEntityMock.getStringField(UserFields.ROLE)).willReturn(to);
        given(userEntityMock.getField(UserFields.ROLE)).willReturn(to);
    }

    private void stubCurrentUserRole(final String role) {
        given(currentUserEntityMock.getStringField(UserFields.ROLE)).willReturn(role);
        given(currentUserEntityMock.getField(UserFields.ROLE)).willReturn(role);
    }

    private void stubSecurityContextWithAuthentication() {
        final Authentication authentication = mock(Authentication.class);
        given(securityContext.getAuthentication()).willReturn(authentication);
    }

    @Test
    public final void shouldMarkTransitionFromNullToSuperadminAsInvalidWhenPerformedByNonSuperadminDuringCreation() {
        // given
        stubSecurityContextWithAuthentication();
        stubCurrentUserRole(ROLE_ADMIN);
        stubRoleTransition(null, ROLE_SUPERADMIN);

        given(userEntityMock.getId()).willReturn(null);

        // when
        final boolean isValid = userRoleValidationService.checkUserCreatingSuperadmin(userDataDefMock, userEntityMock);

        // then
        assertFalse(isValid);
        verify(userEntityMock).addError(Mockito.eq(userRoleFieldDefMock), Mockito.anyString());
    }

    @Test
    public final void shouldMarkTransitionFromNullToSuperadminAsValidWhenPerformedBySuperadminDuringCreation() {
        // given
        stubSecurityContextWithAuthentication();
        stubCurrentUserRole(ROLE_SUPERADMIN);
        stubRoleTransition(null, ROLE_SUPERADMIN);

        given(userEntityMock.getId()).willReturn(null);

        // when
        final boolean isValid = userRoleValidationService.checkUserCreatingSuperadmin(userDataDefMock, userEntityMock);

        // then
        assertTrue(isValid);
    }

    @Test
    public final void shouldMarkTransitionFromNullToSuperadminAsValidWhenPerformedByShopDuringCreation() {
        // given
        stubSecurityContextWithAuthentication();
        stubCurrentUserRole(ROLE_SUPERADMIN);
        stubRoleTransition(null, ROLE_SUPERADMIN);

        given(userEntityMock.getId()).willReturn(null);

        // when
        final boolean isValid = userRoleValidationService.checkUserCreatingSuperadmin(userDataDefMock, userEntityMock);

        // then
        assertTrue(isValid);
    }

    @Test
    public final void shouldMarkTransitionFromNullToSuperadminAsInvalidWhenPerformedByNonSuperadmin() {
        // given
        stubSecurityContextWithAuthentication();
        stubCurrentUserRole(ROLE_ADMIN);
        stubRoleTransition(null, ROLE_SUPERADMIN);

        // when
        final boolean isValid = userRoleValidationService.checkUserCreatingSuperadmin(userDataDefMock, userEntityMock);

        // then
        assertFalse(isValid);
        verify(userEntityMock).addError(Mockito.eq(userRoleFieldDefMock), Mockito.anyString());
    }

    @Test
    public final void shouldMarkTransitionFromNullToSuperadminAsValidWhenPerformedBySuperadmin() {
        // given
        stubSecurityContextWithAuthentication();
        stubCurrentUserRole(ROLE_SUPERADMIN);
        stubRoleTransition(null, ROLE_SUPERADMIN);

        // when
        final boolean isValid = userRoleValidationService.checkUserCreatingSuperadmin(userDataDefMock, userEntityMock);

        // then
        assertTrue(isValid);
    }

    @Test
    public final void shouldMarkTransitionFromNullToSuperadminAsValidWhenPerformedByShop() {
        // given
        stubSecurityContextWithAuthentication();
        stubCurrentUserRole(ROLE_SUPERADMIN);
        stubRoleTransition(null, ROLE_SUPERADMIN);

        // when
        final boolean isValid = userRoleValidationService.checkUserCreatingSuperadmin(userDataDefMock, userEntityMock);

        // then
        assertTrue(isValid);
    }

    @Test
    public final void shouldMarkTransitionFromBlankToSuperadminAsValidWhenPerformedByNonSuperadmin() {
        // given
        stubSecurityContextWithAuthentication();
        stubCurrentUserRole(ROLE_ADMIN);
        stubRoleTransition(" ", ROLE_SUPERADMIN);

        // when
        final boolean isValid = userRoleValidationService.checkUserCreatingSuperadmin(userDataDefMock, userEntityMock);

        // then
        assertFalse(isValid);
        verify(userEntityMock).addError(Mockito.eq(userRoleFieldDefMock), Mockito.anyString());
    }

    @Test
    public final void shouldMarkTransitionFromBlankToSuperadminAsValidWhenPerformedBySuperadmin() {
        // given
        stubSecurityContextWithAuthentication();
        stubCurrentUserRole(ROLE_SUPERADMIN);
        stubRoleTransition(" ", ROLE_SUPERADMIN);

        // when
        final boolean isValid = userRoleValidationService.checkUserCreatingSuperadmin(userDataDefMock, userEntityMock);

        // then
        assertTrue(isValid);
    }

    @Test
    public final void shouldMarkTransitionFromBlankToSuperadminAsValidWhenPerformedByShop() {
        stubRoleTransition(" ", ROLE_SUPERADMIN);

        // when
        final boolean isValid = userRoleValidationService.checkUserCreatingSuperadmin(userDataDefMock, userEntityMock);

        // then
        assertTrue(isValid);
    }

    @Test
    public final void shouldMarkTransitionFromSuperadminToSuperadminAsValidWhenPerformedByNonSuperadmin() {
        // given
        stubSecurityContextWithAuthentication();
        stubCurrentUserRole(ROLE_ADMIN);
        stubRoleTransition(ROLE_SUPERADMIN, ROLE_SUPERADMIN);

        // when
        final boolean isValid = userRoleValidationService.checkUserCreatingSuperadmin(userDataDefMock, userEntityMock);

        // then
        assertTrue(isValid);
    }

    @Test
    public final void shouldMarkTransitionFromSuperadminToSuperadminAsValidWhenPerformedBySuperadmin() {
        // given
        stubSecurityContextWithAuthentication();
        stubCurrentUserRole(ROLE_SUPERADMIN);
        stubRoleTransition(ROLE_SUPERADMIN, ROLE_SUPERADMIN);

        // when
        final boolean isValid = userRoleValidationService.checkUserCreatingSuperadmin(userDataDefMock, userEntityMock);

        // then
        assertTrue(isValid);
    }

    @Test
    public final void shouldMarkTransitionFromSuperadminToSuperadminAsValidWhenPerformedByShop() {
        stubRoleTransition(ROLE_SUPERADMIN, ROLE_SUPERADMIN);

        // when
        final boolean isValid = userRoleValidationService.checkUserCreatingSuperadmin(userDataDefMock, userEntityMock);

        // then
        assertTrue(isValid);
    }

    @Test
    public final void shouldMarkTransitionFromSuperadminToAnyAsValid() {
        // given
        stubSecurityContextWithAuthentication();
        stubRoleTransition(ROLE_SUPERADMIN, "99anyOtherRole");

        // when
        final boolean isValid = userRoleValidationService.checkUserCreatingSuperadmin(userDataDefMock, userEntityMock);

        // then
        assertTrue(isValid);
    }

    @Test
    public final void shouldMarkTransitionFromNullToRegularAdminAsValid() {
        // given
        stubSecurityContextWithAuthentication();
        stubRoleTransition(null, ROLE_ADMIN);

        // when
        final boolean isValid = userRoleValidationService.checkUserCreatingSuperadmin(userDataDefMock, userEntityMock);

        // then
        assertTrue(isValid);
    }

    @Test
    public final void shouldMarkTransitionFromBlankToRegularAdminAsValid() {
        // given
        stubSecurityContextWithAuthentication();
        stubRoleTransition(" ", ROLE_ADMIN);

        // when
        final boolean isValid = userRoleValidationService.checkUserCreatingSuperadmin(userDataDefMock, userEntityMock);

        // then
        assertTrue(isValid);
    }

    @Test
    public final void shouldMarkTransitionFromNonSuperadminToNonSuperadminAsValid() {
        // given
        stubSecurityContextWithAuthentication();
        stubRoleTransition("99anyOtherRole", "89anyOther");

        // when
        final boolean isValid = userRoleValidationService.checkUserCreatingSuperadmin(userDataDefMock, userEntityMock);

        // then
        assertTrue(isValid);
    }

    @Test
    public final void shouldMarkTransitionFromNonSuperadminToSuperadminAsInvalid() {
        // given
        stubSecurityContextWithAuthentication();
        stubRoleTransition("99anyOtherRole", ROLE_SUPERADMIN);

        // when
        final boolean isValid = userRoleValidationService.checkUserCreatingSuperadmin(userDataDefMock, userEntityMock);

        // then
        assertFalse(isValid);
        verify(userEntityMock).addError(Mockito.eq(userRoleFieldDefMock), Mockito.anyString());
    }

    @Test
    public final void shouldMarkTransitionFromNonSuperadminToSuperadminAsValidWhenPerformedByShop() {
        // given
        stubRoleTransition("99anyOtherRole", ROLE_SUPERADMIN);

        // when
        final boolean isValid = userRoleValidationService.checkUserCreatingSuperadmin(userDataDefMock, userEntityMock);

        // then
        assertTrue(isValid);
    }

    @Test
    public final void shouldMarkTransitionFromNonSuperadminToSuperadminAsValidWhenPerformedBySuperAdmin() {
        // given
        stubCurrentUserRole(ROLE_SUPERADMIN);
        stubRoleTransition("99anyOtherRole", ROLE_SUPERADMIN);

        // when
        final boolean isValid = userRoleValidationService.checkUserCreatingSuperadmin(userDataDefMock, userEntityMock);

        // then
        assertTrue(isValid);
    }

    @Test
    public final void shouldMarkTransitionFromAdminToSuperadminAsInvalid() {
        // given
        stubSecurityContextWithAuthentication();
        stubRoleTransition(ROLE_ADMIN, ROLE_SUPERADMIN);

        // when
        final boolean isValid = userRoleValidationService.checkUserCreatingSuperadmin(userDataDefMock, userEntityMock);

        // then
        assertFalse(isValid);
        verify(userEntityMock).addError(Mockito.eq(userRoleFieldDefMock), Mockito.anyString());
    }

    @Test
    public final void shouldMarkTransitionFromSupervisorToSuperadminAsInvalid() {
        // given
        stubSecurityContextWithAuthentication();
        stubRoleTransition(ROLE_SUPERVISOR, ROLE_SUPERADMIN);

        // when
        final boolean isValid = userRoleValidationService.checkUserCreatingSuperadmin(userDataDefMock, userEntityMock);

        // then
        assertFalse(isValid);
        verify(userEntityMock).addError(Mockito.eq(userRoleFieldDefMock), Mockito.anyString());
    }

    @Test
    public final void shouldMarkTransitionFromUserToSuperadminAsInvalid() {
        // given
        stubSecurityContextWithAuthentication();
        stubRoleTransition(ROLE_USER, ROLE_SUPERADMIN);

        // when
        final boolean isValid = userRoleValidationService.checkUserCreatingSuperadmin(userDataDefMock, userEntityMock);

        // then
        assertFalse(isValid);
        verify(userEntityMock).addError(Mockito.eq(userRoleFieldDefMock), Mockito.anyString());
    }

}
