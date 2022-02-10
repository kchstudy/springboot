package blog.benggri.springboot.config.batch;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BatchDao {

    List<Map<String, Object>> getBatchList(Map<String, Object> prmMap); // 서버별 배치 목록 조회
    int updateBatchStepInfo(Map<String, Object> prmMap); // 배치 실행 단계 정보 수정
    int updateBatchSchInfo(Map<String, Object> prmMap); // 배치 스케쥴 정보 수정

    List<Map<String, Object>> getBatchChgReqList(Map<String, Object> prmMap); // 배치 변경 요청 이력 조회
    int updateBatchChgInfo(Map<String, Object> prmMap); // 배치 변경 요청 이력 수정

    int createBatchExeHis(Map<String, Object> prmMap); // 배치 실행 이력 등록

}
