package org.test.takehome.findhome.service;

import java.util.List;

import org.test.takehome.findhome.model.HomeDetails;

public interface HomeDetailsService {

    public List<HomeDetails> getHighestValueHome(List<String> homeIds);
}
