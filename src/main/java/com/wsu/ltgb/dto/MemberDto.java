package com.wsu.ltgb.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberDto {
    private final Long user_id;
    private final String nickname;
    private final String image;

    public static MemberDto Empty(){
        return new MemberDto(null, null, null);
    }

    public boolean IsEmpty() {
        return this.user_id == null
                && this.nickname == null
                && this.image == null;
    }
}
