package com.example.pennywise.Models;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pennywise.R;

import java.util.List;

public class MonthlyParentModel {
    String parentdate;
    List<MonthlyChildModel> childModelList;

    public MonthlyParentModel(String parentdate,  List<MonthlyChildModel> childModelList) {
        this.parentdate = parentdate;
        this.childModelList = childModelList;
    }


    public String getParentdate() {
        return parentdate;
    }

    public void setParentdate(String parentdate) {
        this.parentdate = parentdate;
    }

    public List<MonthlyChildModel> getChildModelList() {
        return childModelList;
    }

    public void setChildModelList(List<MonthlyChildModel> childModelList) {
        this.childModelList = childModelList;
    }
}
