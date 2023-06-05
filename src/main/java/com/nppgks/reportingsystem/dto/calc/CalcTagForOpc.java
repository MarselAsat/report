package com.nppgks.reportingsystem.dto.calc;


import com.nppgks.reportingsystem.db.calculations.entity.Tag;

public record CalcTagForOpc(Integer id, String address, String permanentName) {
    public static CalcTagForOpc fromTag(Tag tag) {
        return new CalcTagForOpc(tag.getId(), tag.getAddress(), tag.getPermanentName());
    }

    public static Tag toTag(CalcTagForOpc tagForOpc) {
        return new Tag(tagForOpc.id, tagForOpc.permanentName, tagForOpc.address(), null, null, null);
    }
}
