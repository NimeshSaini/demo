package com.utkarshnew.android.Model;


import com.utkarshnew.android.Model.TestPDFData.TestJson;

import java.io.Serializable;

/**
 * Created by admin1 on 28/10/17.
 */

public class VideoTests implements Serializable {
    private TestJson test_json = null;

    public TestJson getTest_json() {
        return test_json;
    }

    public void setTest_json(TestJson test_json) {
        this.test_json = test_json;
    }
}