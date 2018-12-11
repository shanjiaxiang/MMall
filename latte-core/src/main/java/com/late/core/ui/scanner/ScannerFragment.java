package com.late.core.ui.scanner;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.late.core.fragments.LatteFragment;
import com.late.core.ui.camera.RequestCodes;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class ScannerFragment extends LatteFragment implements ZBarScannerView.ResultHandler {
    private ScanView mScanView = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mScanView == null) {
            mScanView = new ScanView(getContext());
        }
        mScanView.setAutoFocus(true);
        mScanView.setResultHandler(this);
    }

    @Override
    public Object setLayout() {
        return mScanView;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mScanView != null) {
            mScanView.startCamera();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mScanView!= null){
            mScanView.stopCameraPreview();
            mScanView.stopCamera();
        }
    }

    @Override
    public void handleResult(Result result) {
        final Bundle bundle = new Bundle();
        bundle.putString("SCAN_RESULT", result.getContents());
        setFragmentResult(RequestCodes.SCAN, bundle);
        getSupportDelegate().pop();
    }
}
