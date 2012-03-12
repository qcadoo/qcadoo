package com.qcadoo.model.internal.file;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.util.Date;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.qcadoo.localization.api.TranslationService;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.tenant.api.MultiTenantUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MultiTenantUtil.class)
public class FileServiceImplTest {

    private FileServiceImpl fileService;

    @Mock
    private TranslationService translationService;

    @Mock
    private File uploadDirectory;

    @Mock
    private DataDefinition dataDefinition;

    @Mock
    private Entity entity;

    @Mock
    private Date date;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        fileService = new FileServiceImpl();

        ReflectionTestUtils.setField(fileService, "translationService", translationService);
        ReflectionTestUtils.setField(fileService, "uploadDirectory", uploadDirectory);

        PowerMockito.mockStatic(MultiTenantUtil.class);
        given(MultiTenantUtil.getCurrentTenantId()).willReturn(0);

        given(uploadDirectory.getAbsolutePath()).willReturn("");
        given(date.getTime()).willReturn(1234567l);
        given(entity.getField("date")).willReturn(date);
        given(entity.getDataDefinition()).willReturn(dataDefinition);
    }

    @Test
    public void shouldAddANewReportFilenameIfThereAreNoneYet() {
        // given
        String filename = "newFileName.abc";
        given(translationService.translate(Mockito.eq(filename), Mockito.any(Locale.class))).willReturn(filename);
        given(entity.getStringField("fileName")).willReturn("");

        // when
        fileService.updateReportFileName(entity, "date", filename);

        // then
        verify(entity).setField("fileName", "/0/newFileName.abc_1970_01_01_01_20_34");
    }

    @Test
    public void shouldStackThatNewReportFilenameToTheCurrentFilenamesAndSeparateThemWithCommas() {
        // given
        String filename = "newFileName.abc";
        given(translationService.translate(Mockito.eq(filename), Mockito.any(Locale.class))).willReturn(filename);
        given(entity.getStringField("fileName")).willReturn("oldFilename");

        // when
        fileService.updateReportFileName(entity, "date", filename);

        // then
        verify(entity).setField("fileName", "oldFilename,/0/newFileName.abc_1970_01_01_01_20_34");
    }
}
