package bookpra.bookcon.dao;

import bookpra.bookcon.entity.Board;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardDAO extends JpaRepository<Board, Integer> {

    @Query("select nvl(max(no),0) + 1 from Board ")
    public int getNextNo();

    @Modifying
    @Query(value = "insert into Board b(b.no,b.title,b.writer,b.pwd,b.content,b.regdate,b.hit,b.ip,b.b_ref,b.b_step,b.b_level) " +
            "values(:#{#b.no},:#{#b.title},:#{#b.writer},:#{#b.pwd},:#{#b.content},sysdate,0,:#{#b.ip},:#{#b.b_ref},:#{#b.b_step},:#{#b.b_level})", nativeQuery = true)
    @Transactional
    public void insert(Board b);

    @Modifying
    @Query("update Board b set b.b_step = b.b_step + 1 where b.b_ref=?1 and b.b_step > ?2")
    @Transactional
    public void updateStep(int b_ref, int b_step);

    //public List<Board> findAllByOrderByB_refDescB_stepAsc();
    @Query("select b from Board b order by b.b_ref desc, b.b_step asc")
    public List<Board> selectAll();
}
