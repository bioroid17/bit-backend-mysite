package com.bitacademy.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bitacademy.mysite.vo.GuestbookVo;

@Repository
public class GuestbookRepository {
//	@Autowired
//	private DataSource dataSource;
	
	@Autowired
	private SqlSession sqlSession;
	
	public boolean insert(GuestbookVo vo) {
		return 1 == sqlSession.insert("guestbook.insert", vo);
	}

	public void delete(String no) {
		sqlSession.delete("guestbook.delete", no);
	}

	public boolean checkPassword(String no, String password) {
		boolean result = false;
		if(password.equals(sqlSession.selectOne("guestbook.checkPassword", no))) {
			result = true;
		}
		
		return result;
	}

	public List<GuestbookVo> findAll() {
		// 호출 시에는 namespace.id
		return sqlSession.selectList("guestbook.findAll");
	}
}
