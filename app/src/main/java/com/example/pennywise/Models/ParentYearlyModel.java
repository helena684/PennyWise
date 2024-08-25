package com.example.pennywise.Models;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pennywise.R;

import java.util.List;

public class ParentYearlyModel {
    String month;
    List<YearlyModel> yearlyModels;

    public ParentYearlyModel(String month, List<YearlyModel> yearlyModels) {
        this.month = month;
        this.yearlyModels = yearlyModels;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<YearlyModel> getYearlyModels() {
        return yearlyModels;
    }

    public void setYearlyModels(List<YearlyModel> yearlyModels) {
        this.yearlyModels = yearlyModels;
    }
}
