/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package binderpool;

import android.os.RemoteException;

/**
 * Created by panhongchao on 15/12/7.
 */
public class SecurityCenterImpl extends ISecurityCenter.Stub {
    private static final char SECRET_CODE = '^';

    @Override
    public String encrypt(String content) throws RemoteException {
        char[] chars = content.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] ^= SECRET_CODE;
        }
        return new String(chars);
    }

    @Override
    public String decrypt(String password) throws RemoteException {
        return encrypt(password);
    }
}
