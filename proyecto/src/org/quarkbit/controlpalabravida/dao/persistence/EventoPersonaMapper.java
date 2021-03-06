package org.quarkbit.controlpalabravida.dao.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.quarkbit.controlpalabravida.dao.domain.EventoPersona;
import org.quarkbit.controlpalabravida.dao.domain.EventoPersonaExample;
import org.quarkbit.controlpalabravida.dao.domain.EventoPersonaKey;

public interface EventoPersonaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table eventos_personas
     *
     * @mbggenerated Mon Jun 06 22:34:46 BOT 2016
     */
    int countByExample(EventoPersonaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table eventos_personas
     *
     * @mbggenerated Mon Jun 06 22:34:46 BOT 2016
     */
    int deleteByExample(EventoPersonaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table eventos_personas
     *
     * @mbggenerated Mon Jun 06 22:34:46 BOT 2016
     */
    int deleteByPrimaryKey(EventoPersonaKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table eventos_personas
     *
     * @mbggenerated Mon Jun 06 22:34:46 BOT 2016
     */
    int insert(EventoPersona record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table eventos_personas
     *
     * @mbggenerated Mon Jun 06 22:34:46 BOT 2016
     */
    int insertSelective(EventoPersona record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table eventos_personas
     *
     * @mbggenerated Mon Jun 06 22:34:46 BOT 2016
     */
    List<EventoPersona> selectByExample(EventoPersonaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table eventos_personas
     *
     * @mbggenerated Mon Jun 06 22:34:46 BOT 2016
     */
    EventoPersona selectByPrimaryKey(EventoPersonaKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table eventos_personas
     *
     * @mbggenerated Mon Jun 06 22:34:46 BOT 2016
     */
    int updateByExampleSelective(@Param("record") EventoPersona record, @Param("example") EventoPersonaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table eventos_personas
     *
     * @mbggenerated Mon Jun 06 22:34:46 BOT 2016
     */
    int updateByExample(@Param("record") EventoPersona record, @Param("example") EventoPersonaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table eventos_personas
     *
     * @mbggenerated Mon Jun 06 22:34:46 BOT 2016
     */
    int updateByPrimaryKeySelective(EventoPersona record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table eventos_personas
     *
     * @mbggenerated Mon Jun 06 22:34:46 BOT 2016
     */
    int updateByPrimaryKey(EventoPersona record);

	void obtenerUltimoId(Map<String, Integer> mapa);
}