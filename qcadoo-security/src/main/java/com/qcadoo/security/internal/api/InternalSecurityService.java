package com.qcadoo.security.internal.api;

import java.util.List;

import com.qcadoo.security.api.SecurityService;

public interface InternalSecurityService extends SecurityService {

    List<QcadooUser> getUsers();

}
