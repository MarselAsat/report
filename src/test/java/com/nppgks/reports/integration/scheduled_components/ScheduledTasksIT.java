package com.nppgks.reports.integration.scheduled_components;

import com.nppgks.reports.entity.TagData;
import com.nppgks.reports.scheduled_components.OpcRequests;
import com.nppgks.reports.scheduled_components.ScheduledTasks;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@ExtendWith(MockitoExtension.class)
public class ScheduledTasksIT {

    @InjectMocks
    private final ScheduledTasks scheduledTasks;
    @SpyBean
    private OpcRequests opcRequests;

    @Autowired
    public ScheduledTasksIT(ScheduledTasks scheduledTasks) {
        this.scheduledTasks = scheduledTasks;
    }

    @Test
    //@Commit
    void generateTagDataMockedOpc(){
        Mockito.doReturn(Map.of("WinCC_OA.report_redu.save", "234", "WinCC_OA.report_redu.main", "890", "WinCC_OA.CRC.Calc_crc", "237"))
                        .when(opcRequests).getTagDataFromOpc(Mockito.anyList());
        List<TagData> tagDataList = scheduledTasks.generateTagDataEveryHour();
        Assertions.assertThat(tagDataList).hasSize(3);
    }

    @Test
    //Commit
    void generateTagData(){
        List<TagData> tagDataList = scheduledTasks.generateTagDataEveryHour();
        Assertions.assertThat(tagDataList).hasSize(3);
    }
}
