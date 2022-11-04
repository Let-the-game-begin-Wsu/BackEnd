package com.wsu.ltgb.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberDto {
    public final Long user_id;
    public final String nickname;
    public final String image;

    public static MemberDto Empty(){
        return new MemberDto(null, null, null);
    }

    public boolean IsEmpty() {
        return this.user_id == null
                && this.nickname == null
                && this.image == null;
    }
}
