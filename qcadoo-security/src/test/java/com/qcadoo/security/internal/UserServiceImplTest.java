package com.qcadoo.security.internal;

import static com.qcadoo.testing.model.EntityTestUtils.stubStringField;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.security.constants.UserFields;

public class UserServiceImplTest {

    private UserServiceImpl userService;

    @Mock
    private Entity userEntity;

    @Mock
    private DataDefinitionService dataDefinitionService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        userService = new UserServiceImpl();
        ReflectionTestUtils.setField(userService, "dataDefinitionService", dataDefinitionService);
    }

    @Test
    public final void shouldReturnFullName() {
        // given
        String firstName = "firstName";
        String lastName = "lastName";
        stubStringField(userEntity, UserFields.FIRST_NAME, firstName);
        stubStringField(userEntity, UserFields.LAST_NAME, lastName);

        // when
        String fullName = userService.extractFullName(userEntity);

        // then
        assertEquals(String.format("%s %s", firstName, lastName), fullName);
    }

    @Test
    public final void shouldReturnNullIfGivenEntityIsNull() {
        // when
        String fullName = userService.extractFullName(null);

        // then
        assertNull(fullName);
    }

}
