package blog.benggri.springboot.comm.backup;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface BackUpDao {

    int backupApi(Map<String, Object> prmMap); /* API */
    int backupBat(Map<String, Object> prmMap); /* 배치 */
    int backupUsr(Map<String, Object> prmMap); /* 사용자 */
    int backupUsrInfo(Map<String, Object> prmMap); /* 사용자_정보 */

}
