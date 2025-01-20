package com.jhf.beatpoker.web.dao.mapper;

import com.jhf.beatpoker.web.dao.entity.PasswordResetRow;

public interface IPasswordResetDao {
    int updateVerificationCode(PasswordResetRow emailResetRow);

    PasswordResetRow getEmailReset(String email);
}
