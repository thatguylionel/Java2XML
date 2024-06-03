package de.tgl.Java2XML.obj;

import java.util.List;

public record Employee(String code, String name, int salary, List<String> names) {
}