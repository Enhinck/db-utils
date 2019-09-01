package com.enhinck.db.entity;

import com.google.common.collect.MapDifference;
import lombok.Data;
import org.apache.commons.collections.map.HashedMap;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Data
public class OneVersionModifySummary {
    List<InformationSchemaTables> addTable = new ArrayList<>();
    Map<String, InformationSchemaTables> map = new HashedMap();
    Map<String, List<InformationSchemaColumns>> adds = new LinkedHashMap<>();
    Map<String, List<MapDifference.ValueDifference<InformationSchemaColumns>>> modifys = new LinkedHashMap<>();
}
