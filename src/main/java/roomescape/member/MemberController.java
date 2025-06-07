package roomescape.member;


import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.member.dto.MemberRequest;
import roomescape.member.dto.MemberResponse;

@RestController
@RequestMapping("/members")
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원 생성", description = "새로운 회원을 생성합니다.")
    @PostMapping
    public ResponseEntity<Void> createMember(
            @RequestBody @Valid MemberRequest request
    ) {
        memberService.createMember(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "모든 회원 조회", description = "등록된 모든 회원의 정보를 조회합니다.")
    @GetMapping
    public ResponseEntity<List<MemberResponse>> readAllMember() {
        final List<MemberResponse> response = memberService.readAllMember();
        return ResponseEntity.ok(response);
    }

}
