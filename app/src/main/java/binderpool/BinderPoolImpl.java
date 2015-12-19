/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package binderpool;

import android.os.IBinder;
import android.os.RemoteException;

/**
 * Created by panhongchao on 15/12/8.
 */
public class BinderPoolImpl extends IBinderPool.Stub {
    @Override
    public IBinder queryBinder(int binderCode) throws RemoteException {
        IBinder binder = null;
        switch (binderCode) {
            case BinderPool.BINDER_SECURITY_CENTER:
                binder = new SecurityCenterImpl();
                break;
            case BinderPool.BINDER_COMPUTE:
                binder = new ComputeImpl();
                break;
            default:
                break;
        }
        return binder;
    }
}
