package com.board;

import com.board.domain.AttachedFile;
import com.board.domain.Board;
import com.board.mapper.AttachedFileMapper;
import com.board.service.AttachedFileService;
import com.board.service.BoardService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

//@Transactional
@SpringBootTest
class TestboardApplicationTests {

//    @BeforeEach
//    static void initAll() {
//        System.out.println("Test시작");
//    }

    @BeforeEach
    void init() {

    }



    @Autowired
    BoardService boardService;
    @Autowired
    AttachedFileService attachedFileService;
    @Autowired
    AttachedFileMapper attachedFileMapper;

    @Test
    void 다운로드 () {
        Long idx = 44L;
        Board board = (Board)boardService.getBoardOne(idx);
        System.out.println("다운로드할 게시글 데이터" + board);
        Long fileIdx = board.getFileIdx();
        attachedFileService.downloadFile(fileIdx);
    }

    @Test
    public ResponseEntity<Resource> downloadFile(Long idx) {
        AttachedFile file = attachedFileMapper.downloadFile(idx);
        String fileName = file.getSaveFileName();
        System.out.println("다운로드 파일이름 : " + fileName);
        String SAVEPATH = "/Users/ddu/Study/testboard/src/main/resources/static/download/";
        Resource resource = new FileSystemResource(SAVEPATH + fileName);

        System.out.println( "resource : " + resource);
        if(!resource.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
            return null;
    }

    @Test
    void sava() {
        Board board = new Board();
        board.setTitle("test제목");
        board.setContent("test내용");
        board.setName("작성자");
        System.out.println("저장 : " + boardService.saveBoard(board));
    }

    @Test
    void getBoardList() {
        System.out.println(boardService);
        Object result = boardService.getBoardList();
        List<Board> boardList = (List<Board>) result;
        if(boardList.size() == 0 ){
            System.out.println("게시글 없음");
        } else {
            System.out.println(boardList);
        }

        Assertions.assertThat(boardList.size()).isGreaterThan(1);
    }

    @Test
    void getBoardOne() {
        Long idx = 2L;
        Optional result =  Optional.ofNullable(boardService.getBoardOne(idx));
        if (result.isPresent()){
            System.out.println(result.get());
        } else {
            System.out.println("결과없음");
        }

    }

   /* @Test
    void modifyBoard(){
        Board board = (Board) boardService.getBoardOne(7);
        board.setTitle("7번 제목수정합니다.");
        board.setName("7번 내용수정합니다 ㅋㄷㅋㄷ");
        System.out.println("board : " + board);
        System.out.println("result : "  + boardService.modifyBoard(board));
    }
    @Test
    void deleteBoard() {
        Board board = (Board) boardService.getBoardOne(7);
        int result = boardService.deleteBoard(board);
        System.out.println("삭제 결과 : " + result);
    }
*/
    @AfterEach
    void tearDown() {
    }

    @AfterAll
    static void tearDownAll() {
    }


}
