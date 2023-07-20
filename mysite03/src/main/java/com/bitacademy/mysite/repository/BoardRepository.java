package com.bitacademy.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bitacademy.mysite.vo.BoardVo;

@Repository
public class BoardRepository {
//	@Autowired
//	private DataSource dataSource;
	
	@Autowired
	private SqlSession sqlSession;
	
	public Boolean plusHit(Long no) {
		return 1 == sqlSession.update("board.plusHit", no);
	}

	public Boolean delete(String no) {
		return 1 == sqlSession.delete("board.delete", no);
	}

	public Boolean write(BoardVo vo) {
		Long groupNo = sqlSession.selectOne("board.getMaxGroupNo");
		vo.setGroupNo(groupNo);
		return 1 == sqlSession.insert("board.write", vo);
	}

	public Boolean update(BoardVo vo) {
		return 1 == sqlSession.update("board.update", vo);
	}

	public Boolean reply(BoardVo vo) {
		sqlSession.update("board.updateHierarchy", vo);
		return 1 == sqlSession.insert("board.reply", vo);
	}

	public BoardVo getArticle(Long no) {
		return sqlSession.selectOne("board.getArticle", no);
	}

	public List<BoardVo> getArticles(Long startNo, Long endNo) {
		Map<String, Object> map = new HashMap<>();
		map.put("startNo", startNo);
		map.put("endNo", endNo);
		return sqlSession.selectList("board.getArticles", map);
	}

	public Long getCount() {
		return sqlSession.selectOne("board.getCount");
	}
}
