package de.tgl.Java2XML.models;

import java.util.List;

public record Employee(String code, String name, int salary, List<String> names) {
}