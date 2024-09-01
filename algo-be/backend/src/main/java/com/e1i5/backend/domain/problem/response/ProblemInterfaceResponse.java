package com.e1i5.backend.domain.problem.response;

import com.e1i5.backend.domain.user.entity.ProfileFileInfo;

public interface ProblemInterfaceResponse {
    String getSiteName();
    String getProblemNum();
    String getProblemTitle();
    String getProblemLevel();
    String getUrl();
}
