package com.bit.springboard.controller;

import com.bit.springboard.dto.BoardDto;
import com.bit.springboard.dto.MemberDto;
import com.bit.springboard.dto.ResponseDto;
import com.bit.springboard.entity.FreeBoard;
import com.bit.springboard.entity.Member;
import com.bit.springboard.service.ApiService;
import com.bit.springboard.service.BoardService;
import com.bit.springboard.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.util.List;

// @RestController = @Controller + @ResponseBody
@RestController
@RequestMapping("/apis")
@RequiredArgsConstructor
public class ApiController {
    private final MemberService memberService;
    private final ApiService apiService;

    // @Operation: API 설명 추가
    @Operation(summary = "Member 등록")
    // @ApiResponses: 상태코드에 대한 설명 추가
    @ApiResponses(value = {
            // @ApiResponse: 각각 상태 코드에 대한 설명 추가
            @ApiResponse(responseCode = "201",
                         description = "Member 등록됨",
                         content = { @Content(mediaType = "application/json",
                                              schema = @Schema(implementation = ResponseDto.class)) }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 파라미터 값",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content
            )
    })
    @PostMapping("/members")
    public ResponseEntity<?> save(
            // @Parameter: Api를 호출할 때 보내는 파라미터에 대한 설명추가
            @Parameter(description = "새로운 사용자의 username, password, email, nickname, tel")
            MemberDto memberDto) {
        ResponseDto<Member> responseDto = new ResponseDto<>();
        try {
            Member savedMember = apiService.save(memberDto);
            responseDto.setStatusCode(HttpStatus.CREATED.value());
            responseDto.setStatusMessage("CREATED");
            responseDto.setData(savedMember);
            return ResponseEntity.created(URI.create("/members")).body(responseDto);
        } catch(Exception e) {
            responseDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }

    @Operation(summary = "모든 Member 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "모든 Member 목록 조회 성공",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content
            )
    })
    @GetMapping("/members")
    public ResponseEntity<?> findAll(@PageableDefault(page = 0, size = 3) Pageable pageable) {
        ResponseDto<Member> responseDto = new ResponseDto<>();

        try {
            Page<Member> memberList = apiService.pageMembers(pageable);
            responseDto.setStatusCode(HttpStatus.OK.value());
            responseDto.setStatusMessage("OK");
            responseDto.setDataPaging(memberList);

            return ResponseEntity.ok(responseDto);
        } catch(Exception e) {
            responseDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }

    @Operation(summary = "특정 Member 조회")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "특정 Member 조회 성공",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ResponseDto.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 파라미터 값",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content
                    )
            }
    )
    @GetMapping("/members/{id}")
    public ResponseEntity<?> findById(@Parameter(description = "조회할 Member의 Id") @PathVariable("id") Long id) {
        ResponseDto<Member> responseDto = new ResponseDto<>();

        try {
            Member member = apiService.findById(id);

            responseDto.setStatusCode(HttpStatus.OK.value());
            responseDto.setStatusMessage("OK");
            responseDto.setData(member);

            return ResponseEntity.ok(responseDto);
        } catch(Exception e) {
            responseDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }

    @Operation(summary = "특정 Member 삭제")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "특정 Member 삭제 성공",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 파라미터 값",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content
                    )
            }
    )
    @DeleteMapping("/members/{id}")
    public ResponseEntity<?> remove(@Parameter(description = "삭제할 Member의 Id") @PathVariable("id") Long id) {
        ResponseDto<Member> responseDto = new ResponseDto<>();

        try {
            apiService.deleteById(id);

            return ResponseEntity.noContent().build();
        } catch(Exception e) {
            responseDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }
    
    @Operation(summary = "특정 Member 수정")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "특정 Member 수정 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 파라미터 값",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content
                    )
            }
    )
    @PatchMapping("/members/{id}")
    public ResponseEntity<?> modify(
            @Parameter(description = "수정할 Member의 Id")
            @PathVariable("id") Long id,
            // x-www-form-urlencoded형태는
            // @ModelAttribute나 @RequestParam으로
            // 데이터를 전송받아 사용했다.
            // 전송되는 데이터의 형태가
            // application/json 형태면
            // @RequestBody 어노테이션을 사용한다.
            @Parameter(description = "Member의 수정될 password, nickname, email, tel")
            MemberDto memberDto) {
        ResponseDto<Member> responseDto = new ResponseDto<>();

        try {
            Member modifiedMember = apiService.modify(id, memberDto);

            responseDto.setStatusCode(HttpStatus.OK.value());
            responseDto.setStatusMessage("OK");
            responseDto.setData(modifiedMember);

            return ResponseEntity.ok(responseDto);
        } catch(Exception e) {
            responseDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }

    @PostMapping("/boards")
    public ResponseEntity<?> post(BoardDto boardDto, MultipartFile[] uploadFiles) {
        ResponseDto<FreeBoard> responseDto = new ResponseDto<>();

        try {
            FreeBoard freeBoard = apiService.post(boardDto, uploadFiles);

            responseDto.setStatusCode(201);
            responseDto.setStatusMessage("created");
            responseDto.setData(freeBoard);

            return ResponseEntity.created(URI.create("/boards")).body(responseDto);
        } catch(Exception e) {
            responseDto.setStatusCode(500);
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }

    @GetMapping("/boards/{id}")
    public ResponseEntity<?> boardDetail(@PathVariable("id") Long id) {
        ResponseDto<FreeBoard> responseDto = new ResponseDto<>();

        try {
            FreeBoard freeBoard = apiService.findFreeBoardById(id);

            responseDto.setStatusCode(200);
            responseDto.setStatusMessage("OK");
            responseDto.setData(freeBoard);

            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            responseDto.setStatusCode(500);
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }

    @GetMapping("/boards-titles")
    public ResponseEntity<?> boardTitles(@RequestParam("title") String title) {
        ResponseDto<FreeBoard> responseDto = new ResponseDto<>();

        try {
            FreeBoard freeBoard = apiService.findByTitle(title);

            responseDto.setStatusCode(200);
            responseDto.setStatusMessage("OK");
            responseDto.setData(freeBoard);

            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            responseDto.setStatusCode(500);
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }

    @GetMapping("/members-username-email")
    public ResponseEntity<?> findByUsernameEmail(@RequestParam("username") String username,
                                                 @RequestParam("email") String email) {
        ResponseDto<Member> responseDto = new ResponseDto<>();

        try {
            List<Member> memberList = apiService.findByUsernameOrEmail(username, email);

            responseDto.setStatusCode(200);
            responseDto.setStatusMessage("OK");
            responseDto.setDataList(memberList);

            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            responseDto.setStatusCode(500);
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }

    @GetMapping("/members-username-like")
    public ResponseEntity<?> findByUsernameLike(@RequestParam("username") String username) {
        ResponseDto<Member> responseDto = new ResponseDto<>();

        try {
            List<Member> memberList = apiService.findByUsernameLike(username);

            responseDto.setStatusCode(200);
            responseDto.setStatusMessage("OK");
            responseDto.setDataList(memberList);

            return ResponseEntity.ok(responseDto);

        } catch (Exception e) {
            responseDto.setStatusCode(500);
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }

    @GetMapping("/members-id-between")
    public ResponseEntity<?> findByIdBetween(@RequestParam("startId") Long startId,
                                             @RequestParam("endId") Long endId) {
        ResponseDto<Member> responseDto = new ResponseDto<>();

        try {
            List<Member> memberList = apiService.findByIdBetween(startId, endId);

            responseDto.setStatusCode(200);
            responseDto.setStatusMessage("OK");
            responseDto.setDataList(memberList);

            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            responseDto.setStatusCode(500);
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }

    @GetMapping("/boards-username")
    public ResponseEntity<?> findByMemberUsername(@RequestParam("username") String username) {
        ResponseDto<FreeBoard> responseDto = new ResponseDto<>();

        try {
            List<FreeBoard> freeBoardList = apiService.findByMemberUsername(username);

            responseDto.setStatusCode(200);
            responseDto.setStatusMessage("OK");
            responseDto.setDataList(freeBoardList);

            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            responseDto.setStatusCode(500);
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }

    @GetMapping("/members-email")
    public ResponseEntity<?> findByEmail(@RequestParam("email") String email){
        ResponseDto<Member> responseDto = new ResponseDto<>();

        try {
            List<Member> memberList = apiService.findByEmail(email);

            responseDto.setStatusCode(200);
            responseDto.setStatusMessage("OK");
            responseDto.setDataList(memberList);

            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            responseDto.setStatusCode(500);
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }

    @GetMapping("/members-id-nickname")
    public ResponseEntity<?> findByBiggerThanNicknameContaining(
                                    @RequestParam("id") Long id,
                                    @RequestParam("nickname") String nickname){
        ResponseDto<Member> responseDto = new ResponseDto<>();

        try {
            List<Member> memberList = apiService.findBiggerThanNicknameContaining(id, nickname);

            responseDto.setStatusCode(200);
            responseDto.setStatusMessage("OK");
            responseDto.setDataList(memberList);

            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            responseDto.setStatusCode(500);
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }
}
