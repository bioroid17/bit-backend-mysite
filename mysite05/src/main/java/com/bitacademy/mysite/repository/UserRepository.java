package com.bitacademy.mysite.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bitacademy.mysite.vo.UserVo;

@Repository
public class UserRepository {
	
	@Autowired
	private SqlSession sqlSession;

	public Boolean insert(UserVo vo) {
		return 1 == sqlSession.insert("user.insert", vo);
	}
	
	public void update(UserVo vo) {
		sqlSession.update("user.update", vo);
	}

	public UserVo findByEmailAndPassword(String email, String password) {
		Map<String, Object> map = new HashMap<>();
		map.put("email", email);
		map.put("password", password);
		return sqlSession.selectOne("user.findByEmailAndPassword", map);
	}
	
	public UserVo findByEmailAndPassword(UserVo vo) {
		return findByEmailAndPassword(vo.getEmail(), vo.getPassword());
	}
	
	public UserVo findByNo(Long no) {
		return sqlSession.selectOne("user.findByNo", no);
	}
	
	public Boolean checkPassword(String no, String password) {
		Map<String, Object> map = new HashMap<>();
		map.put("no", no);
		map.put("password", password);
		return sqlSession.selectOne("user.checkPassword", map);
	}
}
