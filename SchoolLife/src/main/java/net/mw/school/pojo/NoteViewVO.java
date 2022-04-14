package net.mw.school.pojo;

import lombok.Data;
import net.mw.school.pojo.vo.NoteVO;

@Data
public class NoteViewVO extends NoteVO {
    private String user;
}
