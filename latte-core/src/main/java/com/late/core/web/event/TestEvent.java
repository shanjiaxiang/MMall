package com.late.core.web.event;

import android.widget.Toast;

public class TestEvent extends Event{

    @Override
    public String execute(String params) {
        Toast.makeText(getContext(), params, Toast.LENGTH_SHORT).show();
        return null;
    }
}
