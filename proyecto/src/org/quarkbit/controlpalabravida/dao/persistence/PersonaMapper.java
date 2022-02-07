package org.quarkbit.controlpalabravida.dao.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.quarkbit.controlpalabravida.dao.domain.Persona;
import org.quarkbit.controlpalabravida.dao.domain.PersonaExample;

public interface PersonaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table personas
     *
     * @mbggenerated Fri Jun 03 02:37:34 BOT 2016
     */
    int countByExample(PersonaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table personas
     *
     * @mbggenerated Fri Jun 03 02:37:34 BOT 2016
     */
    int deleteByExample(PersonaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table personas
     *
     * @mbggenerated Fri Jun 03 02:37:34 BOT 2016
     */
    int deleteByPrimaryKey(Integer idPersona);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table personas
     *
     * @mbggenerated Fri Jun 03 02:37:34 BOT 2016
     */
    int insert(Persona record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table personas
     *
     * @mbggenerated Fri Jun 03 02:37:34 BOT 2016
     */
    int insertSelective(Persona record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table personas
     *
     * @mbggenerated Fri Jun 03 02:37:34 BOT 2016
     */
    List<Persona> selectByExample(PersonaExample example);
    
    
    //metodos personalizados
    List<Persona> selectParticipantesEventosByPrimaryKey(Integer idPersona);
    List<Persona> selectBuscarParticipantesEventosByPrimaryKey(PersonaExample example);
    List<Persona> selectParticipantesEventosPermisosByPrimaryKey(Integer idPersona);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table personas
     *
     * @mbggenerated Fri Jun 03 02:37:34 BOT 2016
     */
    Persona selectByPrimaryKey(Integer idPersona);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table personas
     *
     * @mbggenerated Fri Jun 03 02:37:34 BOT 2016
     */
    int updateByExampleSelective(@Param("record") Persona record, @Param("example") PersonaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table personas
     *
     * @mbggenerated Fri Jun 03 02:37:34 BOT 2016
     */
    int updateByExample(@Param("record") Persona record, @Param("example") PersonaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table personas
     *
     * @mbggenerated Fri Jun 03 02:37:34 BOT 2016
     */
    int updateByPrimaryKeySelective(Persona record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table personas
     *
     * @mbggenerated Fri Jun 03 02:37:34 BOT 2016
     */
    int updateByPrimaryKey(Persona record);

	int obtenerUltimoId(Map<String, Integer> mapa);
}