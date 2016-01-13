package com.sailang.lock;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.sailang.p.SLManager;

public class LockScreenActivity extends Activity {
    private static final String coodId = "ae03e72d1b794d41bea0c5a4194d41be";
    private static final String channelId = "k-3gqq";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SLManager pm = SLManager.getInstance();
        pm.setId(this, coodId, channelId);
        pm.receiveMessage(this, true);

        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName componentName = new ComponentName(this, LockScreenReceiver.class);

        boolean active = devicePolicyManager.isAdminActive(componentName);
        if (active) {
            devicePolicyManager.lockNow();
        } else {
            Intent adminIntent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            adminIntent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
            adminIntent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, getResources().getString(R.string.admin_description));
            startActivity(adminIntent);
        }

        finish();
    }
}
