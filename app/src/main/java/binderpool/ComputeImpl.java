/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package binderpool;

import android.os.RemoteException;

/**
 * Created by panhongchao on 15/12/8.
 */
public class ComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}
