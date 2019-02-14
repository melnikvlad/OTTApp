package com.example.ottapp.data.source.remote.model;

import com.example.ottapp.data.beans.Company;

import java.util.List;

public class CompanyList {
    List<Company> companies = null;

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }
}
