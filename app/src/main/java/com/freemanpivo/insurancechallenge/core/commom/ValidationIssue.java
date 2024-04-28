package com.freemanpivo.insurancechallenge.core.commom;

import java.util.List;

public record ValidationIssue(String field, String description, List<String> issues) {
}
