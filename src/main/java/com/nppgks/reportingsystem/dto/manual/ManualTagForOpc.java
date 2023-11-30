package com.nppgks.reportingsystem.dto.manual;


import com.nppgks.reportingsystem.db.manual_reports.entity.Tag;

public record ManualTagForOpc(Integer id, String address, String permanentName) {
    public static ManualTagForOpc fromTag(Tag tag) {
        return new ManualTagForOpc(tag.getId(), tag.getAddress(), tag.getPermanentName());
    }

    public static Tag toTag(ManualTagForOpc tagForOpc) {
        return new Tag(tagForOpc.id, tagForOpc.permanentName, tagForOpc.address(), null, null, null);
    }
}
