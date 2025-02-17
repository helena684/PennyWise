package com.example.pennywise.Models;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pennywise.R;

public class CurrencyModel {
    String name;
    String symbol_native;
    String abbr;
    String code;

    public CurrencyModel(String name, String symbol_native, String abbr, String code) {
        this.name = name;
        this.symbol_native = symbol_native;
        this.abbr = abbr;
        this.code = code;
    }

    public CurrencyModel(String name, String symbol_native, String abbr) {
        this.name = name;
        this.symbol_native = symbol_native;
        this.abbr = abbr;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol_native() {
        return symbol_native;
    }

    public void setSymbol_native(String symbol_native) {
        this.symbol_native = symbol_native;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }
}
