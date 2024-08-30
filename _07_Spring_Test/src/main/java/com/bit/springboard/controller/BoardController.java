package com.bit.springboard.controller;

import com.bit.springboard.dto.*;
import com.bit.springboard.entity.CustomUserDetails;
import com.bit.springboard.service.BoardService;
import com.bit.springboard.service.impl.FreeServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {
    private BoardService boardService;
    private final ApplicationContext applicationContext;

    @GetMapping("/free-list")
    public ModelAndView freeListView(@RequestParam Map<String, String> searchMap, Criteria cri,
                                     @PageableDefault(page = 0, size = 10) Pageable pageable) {
        ModelAndView mav = new ModelAndView();

        boardService = applicationContext.getBean("freeServiceImpl", BoardService.class);

//        mav.addObject("freeList", boardService.findAll(searchMap, cri));
        mav.addObject("freeList", boardService.findAll(searchMap, pageable));
        mav.addObject("searchMap", searchMap);

//        int total = boardService.findTotalCnt(searchMap);

//        mav.addObject("page", new PageDto(cri, total));

        mav.setViewName("board/free-list");
        return mav;
    }

    @GetMapping("/{id}")
    public ModelAndView boardDetail(@PathVariable Long id,
                                    @RequestParam("type") String type) {
        ModelAndView mav = new ModelAndView();

        if(type.equals("free")) {
            boardService = applicationContext.getBean("freeServiceImpl", BoardService.class);
            mav.setViewName("board/free-detail");
        } else if(type.equals("notice")) {
            boardService = applicationContext.getBean("noticeServiceImpl", BoardService.class);
            mav.setViewName("board/notice-detail");
        }

//        System.out.println(boardService.findById(id));
        mav.addObject("board", boardService.findById(id));
//        mav.addObject("fileList", boardService.findFilesById(id));

        return mav;
    }

    @GetMapping("/notice-list")
    public ModelAndView noticeListView(@RequestParam Map<String, String> searchMap,
                                       @PageableDefault(page = 0, size = 9) Pageable pageable) {
        boardService = applicationContext.getBean("noticeServiceImpl", BoardService.class);

        ModelAndView mav = new ModelAndView();

        List<Map<String, Object>> noticeList = new ArrayList<>();

        mav.addObject("noticeList", boardService.findAll(searchMap, pageable));
        mav.addObject("searchMap", searchMap);

        int total = boardService.findTotalCnt(searchMap);

        mav.setViewName("board/notice-list");
        return mav;
    }

    @GetMapping("/post")
    // HttpSeesion을 사용해서 로그인 처리를 했을 때는
    // session.getAttribute를 이용해서 로그인한 사용자의 정보를 Controller에서 받아서 사용했는데
    // SpringSecurity에서는 @authenticationPrincipal 어노테이션을 이용하여
    // Security Context에 저장되어 있는 CustomeUserDetails를 바로 꺼내서 사용한다.
    public ModelAndView postView(HttpServletResponse response,
                                 @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ModelAndView mav = new ModelAndView();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CustomUserDetails userDetails = (CustomUserDetails) principal;

        System.out.println(userDetails.getUsername());

        System.out.println(customUserDetails.getUsername());
        try {
             mav.setViewName("board/post");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return mav;
    }

    @PostMapping
    public ResponseEntity<?> post(BoardDto boardDto, MultipartFile[] uploadFiles) {
        ResponseDto<BoardDto> responseDto = new ResponseDto<>();

        if(boardDto.getType().equals("free")) {
            boardService = applicationContext.getBean("freeServiceImpl", BoardService.class);
        } else {
            boardService = applicationContext.getBean("noticeServiceImpl", BoardService.class);
        }

        try {
            BoardDto returnBoardDto = boardService.post(boardDto, uploadFiles);
            returnBoardDto.setType(boardDto.getType());

            responseDto.setStatusCode(201);
            responseDto.setStatusMessage("created");
            responseDto.setData(returnBoardDto);

            return ResponseEntity.created(URI.create("/boards")).body(responseDto);
        } catch(Exception e) {
            responseDto.setStatusCode(500);
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }


    @PatchMapping("{id}")
    public ResponseEntity<?> modify(@PathVariable("id") Long id
                                  , BoardDto boardDto
                                  , MultipartFile[] uploadFiles
                                  , MultipartFile[] changeFiles
                                  , @RequestParam(name = "originFiles", required = false) String originFiles) {
        ResponseDto<BoardDto> responseDto = new ResponseDto<>();

        if(boardDto.getType().equals("free")) {
            boardService = applicationContext.getBean("freeServiceImpl", BoardService.class);
        } else {
            boardService = applicationContext.getBean("noticeServiceImpl", BoardService.class);
        }

        try {
            boardDto.setId(id);
            BoardDto modifiedBoardDto = boardService.modify(boardDto, uploadFiles, changeFiles, originFiles);

            responseDto.setStatusCode(200);
            responseDto.setStatusMessage("OK");
            responseDto.setData(modifiedBoardDto);

            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            responseDto.setStatusCode(500);
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }

    @GetMapping("/cnt/{id}")
    public void updateBoardCnt(@PathVariable("id") Long id,
                               @RequestParam("type") String type,
                               HttpServletResponse response) {
        if(type.equals("free")) {
            boardService = applicationContext.getBean("freeServiceImpl", BoardService.class);
        } else {
            boardService = applicationContext.getBean("noticeServiceImpl", BoardService.class);
        }

        boardService.updateBoardCnt(id);

        try {
            response.sendRedirect("/boards/" + id + "?type=" + type);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable("id") Long id,
                                    @RequestParam("type") String type) {
        if(type.equals("free")) {
            boardService = applicationContext.getBean("freeServiceImpl", BoardService.class);
        } else {
            boardService = applicationContext.getBean("noticeServiceImpl", BoardService.class);
        }

        try {
            boardService.remove(id);

            return ResponseEntity.noContent().build();
        } catch(Exception e) {
            ResponseDto<BoardDto> responseDto = new ResponseDto<>();

            responseDto.setStatusCode(500);
            responseDto.setStatusMessage(e.getMessage());

            return ResponseEntity.internalServerError().body(responseDto);
        }
    }

    @PostMapping("/notice-list-ajax")
    public ResponseEntity<?> noticeList(@RequestParam Map<String, String> searchMap,
                                        @PageableDefault(page = 0, size = 9) Pageable pageable) {
        ResponseDto<BoardDto> responseDto = new ResponseDto<>();

        try {
            boardService = applicationContext.getBean("noticeServiceImpl", BoardService.class);

            responseDto.setStatusCode(200);
            responseDto.setStatusMessage("OK");
            responseDto.setDataPaging(boardService.findAll(searchMap, pageable));

            return ResponseEntity.ok(responseDto);
        } catch(Exception e){
            responseDto.setStatusCode(500);
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }
}
