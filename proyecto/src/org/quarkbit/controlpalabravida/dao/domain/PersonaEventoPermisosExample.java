package org.quarkbit.controlpalabravida.dao.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PersonaEventoPermisosExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table personas_eventos_permisos
     *
     * @mbggenerated Mon Jun 13 11:29:50 BOT 2016
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table personas_eventos_permisos
     *
     * @mbggenerated Mon Jun 13 11:29:50 BOT 2016
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table personas_eventos_permisos
     *
     * @mbggenerated Mon Jun 13 11:29:50 BOT 2016
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table personas_eventos_permisos
     *
     * @mbggenerated Mon Jun 13 11:29:50 BOT 2016
     */
    public PersonaEventoPermisosExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table personas_eventos_permisos
     *
     * @mbggenerated Mon Jun 13 11:29:50 BOT 2016
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table personas_eventos_permisos
     *
     * @mbggenerated Mon Jun 13 11:29:50 BOT 2016
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table personas_eventos_permisos
     *
     * @mbggenerated Mon Jun 13 11:29:50 BOT 2016
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table personas_eventos_permisos
     *
     * @mbggenerated Mon Jun 13 11:29:50 BOT 2016
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table personas_eventos_permisos
     *
     * @mbggenerated Mon Jun 13 11:29:50 BOT 2016
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table personas_eventos_permisos
     *
     * @mbggenerated Mon Jun 13 11:29:50 BOT 2016
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table personas_eventos_permisos
     *
     * @mbggenerated Mon Jun 13 11:29:50 BOT 2016
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table personas_eventos_permisos
     *
     * @mbggenerated Mon Jun 13 11:29:50 BOT 2016
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table personas_eventos_permisos
     *
     * @mbggenerated Mon Jun 13 11:29:50 BOT 2016
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table personas_eventos_permisos
     *
     * @mbggenerated Mon Jun 13 11:29:50 BOT 2016
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table personas_eventos_permisos
     *
     * @mbggenerated Mon Jun 13 11:29:50 BOT 2016
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andId_permisoIsNull() {
            addCriterion("id_permiso is null");
            return (Criteria) this;
        }

        public Criteria andId_permisoIsNotNull() {
            addCriterion("id_permiso is not null");
            return (Criteria) this;
        }

        public Criteria andId_permisoEqualTo(Integer value) {
            addCriterion("id_permiso =", value, "id_permiso");
            return (Criteria) this;
        }

        public Criteria andId_permisoNotEqualTo(Integer value) {
            addCriterion("id_permiso <>", value, "id_permiso");
            return (Criteria) this;
        }

        public Criteria andId_permisoGreaterThan(Integer value) {
            addCriterion("id_permiso >", value, "id_permiso");
            return (Criteria) this;
        }

        public Criteria andId_permisoGreaterThanOrEqualTo(Integer value) {
            addCriterion("id_permiso >=", value, "id_permiso");
            return (Criteria) this;
        }

        public Criteria andId_permisoLessThan(Integer value) {
            addCriterion("id_permiso <", value, "id_permiso");
            return (Criteria) this;
        }

        public Criteria andId_permisoLessThanOrEqualTo(Integer value) {
            addCriterion("id_permiso <=", value, "id_permiso");
            return (Criteria) this;
        }

        public Criteria andId_permisoIn(List<Integer> values) {
            addCriterion("id_permiso in", values, "id_permiso");
            return (Criteria) this;
        }

        public Criteria andId_permisoNotIn(List<Integer> values) {
            addCriterion("id_permiso not in", values, "id_permiso");
            return (Criteria) this;
        }

        public Criteria andId_permisoBetween(Integer value1, Integer value2) {
            addCriterion("id_permiso between", value1, value2, "id_permiso");
            return (Criteria) this;
        }

        public Criteria andId_permisoNotBetween(Integer value1, Integer value2) {
            addCriterion("id_permiso not between", value1, value2, "id_permiso");
            return (Criteria) this;
        }

        public Criteria andId_eventoIsNull() {
            addCriterion("id_evento is null");
            return (Criteria) this;
        }

        public Criteria andId_eventoIsNotNull() {
            addCriterion("id_evento is not null");
            return (Criteria) this;
        }

        public Criteria andId_eventoEqualTo(Integer value) {
            addCriterion("id_evento =", value, "id_evento");
            return (Criteria) this;
        }

        public Criteria andId_eventoNotEqualTo(Integer value) {
            addCriterion("id_evento <>", value, "id_evento");
            return (Criteria) this;
        }

        public Criteria andId_eventoGreaterThan(Integer value) {
            addCriterion("id_evento >", value, "id_evento");
            return (Criteria) this;
        }

        public Criteria andId_eventoGreaterThanOrEqualTo(Integer value) {
            addCriterion("id_evento >=", value, "id_evento");
            return (Criteria) this;
        }

        public Criteria andId_eventoLessThan(Integer value) {
            addCriterion("id_evento <", value, "id_evento");
            return (Criteria) this;
        }

        public Criteria andId_eventoLessThanOrEqualTo(Integer value) {
            addCriterion("id_evento <=", value, "id_evento");
            return (Criteria) this;
        }

        public Criteria andId_eventoIn(List<Integer> values) {
            addCriterion("id_evento in", values, "id_evento");
            return (Criteria) this;
        }

        public Criteria andId_eventoNotIn(List<Integer> values) {
            addCriterion("id_evento not in", values, "id_evento");
            return (Criteria) this;
        }

        public Criteria andId_eventoBetween(Integer value1, Integer value2) {
            addCriterion("id_evento between", value1, value2, "id_evento");
            return (Criteria) this;
        }

        public Criteria andId_eventoNotBetween(Integer value1, Integer value2) {
            addCriterion("id_evento not between", value1, value2, "id_evento");
            return (Criteria) this;
        }

        public Criteria andId_personaIsNull() {
            addCriterion("id_persona is null");
            return (Criteria) this;
        }

        public Criteria andId_personaIsNotNull() {
            addCriterion("id_persona is not null");
            return (Criteria) this;
        }

        public Criteria andId_personaEqualTo(Integer value) {
            addCriterion("id_persona =", value, "id_persona");
            return (Criteria) this;
        }

        public Criteria andId_personaNotEqualTo(Integer value) {
            addCriterion("id_persona <>", value, "id_persona");
            return (Criteria) this;
        }

        public Criteria andId_personaGreaterThan(Integer value) {
            addCriterion("id_persona >", value, "id_persona");
            return (Criteria) this;
        }

        public Criteria andId_personaGreaterThanOrEqualTo(Integer value) {
            addCriterion("id_persona >=", value, "id_persona");
            return (Criteria) this;
        }

        public Criteria andId_personaLessThan(Integer value) {
            addCriterion("id_persona <", value, "id_persona");
            return (Criteria) this;
        }

        public Criteria andId_personaLessThanOrEqualTo(Integer value) {
            addCriterion("id_persona <=", value, "id_persona");
            return (Criteria) this;
        }

        public Criteria andId_personaIn(List<Integer> values) {
            addCriterion("id_persona in", values, "id_persona");
            return (Criteria) this;
        }

        public Criteria andId_personaNotIn(List<Integer> values) {
            addCriterion("id_persona not in", values, "id_persona");
            return (Criteria) this;
        }

        public Criteria andId_personaBetween(Integer value1, Integer value2) {
            addCriterion("id_persona between", value1, value2, "id_persona");
            return (Criteria) this;
        }

        public Criteria andId_personaNotBetween(Integer value1, Integer value2) {
            addCriterion("id_persona not between", value1, value2, "id_persona");
            return (Criteria) this;
        }

        public Criteria andId_usuarioIsNull() {
            addCriterion("id_usuario is null");
            return (Criteria) this;
        }

        public Criteria andId_usuarioIsNotNull() {
            addCriterion("id_usuario is not null");
            return (Criteria) this;
        }

        public Criteria andId_usuarioEqualTo(Integer value) {
            addCriterion("id_usuario =", value, "id_usuario");
            return (Criteria) this;
        }

        public Criteria andId_usuarioNotEqualTo(Integer value) {
            addCriterion("id_usuario <>", value, "id_usuario");
            return (Criteria) this;
        }

        public Criteria andId_usuarioGreaterThan(Integer value) {
            addCriterion("id_usuario >", value, "id_usuario");
            return (Criteria) this;
        }

        public Criteria andId_usuarioGreaterThanOrEqualTo(Integer value) {
            addCriterion("id_usuario >=", value, "id_usuario");
            return (Criteria) this;
        }

        public Criteria andId_usuarioLessThan(Integer value) {
            addCriterion("id_usuario <", value, "id_usuario");
            return (Criteria) this;
        }

        public Criteria andId_usuarioLessThanOrEqualTo(Integer value) {
            addCriterion("id_usuario <=", value, "id_usuario");
            return (Criteria) this;
        }

        public Criteria andId_usuarioIn(List<Integer> values) {
            addCriterion("id_usuario in", values, "id_usuario");
            return (Criteria) this;
        }

        public Criteria andId_usuarioNotIn(List<Integer> values) {
            addCriterion("id_usuario not in", values, "id_usuario");
            return (Criteria) this;
        }

        public Criteria andId_usuarioBetween(Integer value1, Integer value2) {
            addCriterion("id_usuario between", value1, value2, "id_usuario");
            return (Criteria) this;
        }

        public Criteria andId_usuarioNotBetween(Integer value1, Integer value2) {
            addCriterion("id_usuario not between", value1, value2, "id_usuario");
            return (Criteria) this;
        }

        public Criteria andFecha_registroIsNull() {
            addCriterion("fecha_registro is null");
            return (Criteria) this;
        }

        public Criteria andFecha_registroIsNotNull() {
            addCriterion("fecha_registro is not null");
            return (Criteria) this;
        }

        public Criteria andFecha_registroEqualTo(Date value) {
            addCriterionForJDBCDate("fecha_registro =", value, "fecha_registro");
            return (Criteria) this;
        }

        public Criteria andFecha_registroNotEqualTo(Date value) {
            addCriterionForJDBCDate("fecha_registro <>", value, "fecha_registro");
            return (Criteria) this;
        }

        public Criteria andFecha_registroGreaterThan(Date value) {
            addCriterionForJDBCDate("fecha_registro >", value, "fecha_registro");
            return (Criteria) this;
        }

        public Criteria andFecha_registroGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("fecha_registro >=", value, "fecha_registro");
            return (Criteria) this;
        }

        public Criteria andFecha_registroLessThan(Date value) {
            addCriterionForJDBCDate("fecha_registro <", value, "fecha_registro");
            return (Criteria) this;
        }

        public Criteria andFecha_registroLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("fecha_registro <=", value, "fecha_registro");
            return (Criteria) this;
        }

        public Criteria andFecha_registroIn(List<Date> values) {
            addCriterionForJDBCDate("fecha_registro in", values, "fecha_registro");
            return (Criteria) this;
        }

        public Criteria andFecha_registroNotIn(List<Date> values) {
            addCriterionForJDBCDate("fecha_registro not in", values, "fecha_registro");
            return (Criteria) this;
        }

        public Criteria andFecha_registroBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("fecha_registro between", value1, value2, "fecha_registro");
            return (Criteria) this;
        }

        public Criteria andFecha_registroNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("fecha_registro not between", value1, value2, "fecha_registro");
            return (Criteria) this;
        }

        public Criteria andFecha_permisoIsNull() {
            addCriterion("fecha_permiso is null");
            return (Criteria) this;
        }

        public Criteria andFecha_permisoIsNotNull() {
            addCriterion("fecha_permiso is not null");
            return (Criteria) this;
        }

        public Criteria andFecha_permisoEqualTo(Date value) {
            addCriterionForJDBCDate("fecha_permiso =", value, "fecha_permiso");
            return (Criteria) this;
        }

        public Criteria andFecha_permisoNotEqualTo(Date value) {
            addCriterionForJDBCDate("fecha_permiso <>", value, "fecha_permiso");
            return (Criteria) this;
        }

        public Criteria andFecha_permisoGreaterThan(Date value) {
            addCriterionForJDBCDate("fecha_permiso >", value, "fecha_permiso");
            return (Criteria) this;
        }

        public Criteria andFecha_permisoGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("fecha_permiso >=", value, "fecha_permiso");
            return (Criteria) this;
        }

        public Criteria andFecha_permisoLessThan(Date value) {
            addCriterionForJDBCDate("fecha_permiso <", value, "fecha_permiso");
            return (Criteria) this;
        }

        public Criteria andFecha_permisoLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("fecha_permiso <=", value, "fecha_permiso");
            return (Criteria) this;
        }

        public Criteria andFecha_permisoIn(List<Date> values) {
            addCriterionForJDBCDate("fecha_permiso in", values, "fecha_permiso");
            return (Criteria) this;
        }

        public Criteria andFecha_permisoNotIn(List<Date> values) {
            addCriterionForJDBCDate("fecha_permiso not in", values, "fecha_permiso");
            return (Criteria) this;
        }

        public Criteria andFecha_permisoBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("fecha_permiso between", value1, value2, "fecha_permiso");
            return (Criteria) this;
        }

        public Criteria andFecha_permisoNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("fecha_permiso not between", value1, value2, "fecha_permiso");
            return (Criteria) this;
        }

        public Criteria andFechaFinPermisoIsNull() {
            addCriterion("fecha_fin_permiso is null");
            return (Criteria) this;
        }

        public Criteria andFechaFinPermisoIsNotNull() {
            addCriterion("fecha_fin_permiso is not null");
            return (Criteria) this;
        }

        public Criteria andFechaFinPermisoEqualTo(Date value) {
            addCriterionForJDBCDate("fecha_fin_permiso =", value, "fechaFinPermiso");
            return (Criteria) this;
        }

        public Criteria andFechaFinPermisoNotEqualTo(Date value) {
            addCriterionForJDBCDate("fecha_fin_permiso <>", value, "fechaFinPermiso");
            return (Criteria) this;
        }

        public Criteria andFechaFinPermisoGreaterThan(Date value) {
            addCriterionForJDBCDate("fecha_fin_permiso >", value, "fechaFinPermiso");
            return (Criteria) this;
        }

        public Criteria andFechaFinPermisoGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("fecha_fin_permiso >=", value, "fechaFinPermiso");
            return (Criteria) this;
        }

        public Criteria andFechaFinPermisoLessThan(Date value) {
            addCriterionForJDBCDate("fecha_fin_permiso <", value, "fechaFinPermiso");
            return (Criteria) this;
        }

        public Criteria andFechaFinPermisoLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("fecha_fin_permiso <=", value, "fechaFinPermiso");
            return (Criteria) this;
        }

        public Criteria andFechaFinPermisoIn(List<Date> values) {
            addCriterionForJDBCDate("fecha_fin_permiso in", values, "fechaFinPermiso");
            return (Criteria) this;
        }

        public Criteria andFechaFinPermisoNotIn(List<Date> values) {
            addCriterionForJDBCDate("fecha_fin_permiso not in", values, "fechaFinPermiso");
            return (Criteria) this;
        }

        public Criteria andFechaFinPermisoBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("fecha_fin_permiso between", value1, value2, "fechaFinPermiso");
            return (Criteria) this;
        }

        public Criteria andFechaFinPermisoNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("fecha_fin_permiso not between", value1, value2, "fechaFinPermiso");
            return (Criteria) this;
        }

        public Criteria andTipo_permisoIsNull() {
            addCriterion("tipo_permiso is null");
            return (Criteria) this;
        }

        public Criteria andTipo_permisoIsNotNull() {
            addCriterion("tipo_permiso is not null");
            return (Criteria) this;
        }

        public Criteria andTipo_permisoEqualTo(String value) {
            addCriterion("tipo_permiso =", value, "tipo_permiso");
            return (Criteria) this;
        }

        public Criteria andTipo_permisoNotEqualTo(String value) {
            addCriterion("tipo_permiso <>", value, "tipo_permiso");
            return (Criteria) this;
        }

        public Criteria andTipo_permisoGreaterThan(String value) {
            addCriterion("tipo_permiso >", value, "tipo_permiso");
            return (Criteria) this;
        }

        public Criteria andTipo_permisoGreaterThanOrEqualTo(String value) {
            addCriterion("tipo_permiso >=", value, "tipo_permiso");
            return (Criteria) this;
        }

        public Criteria andTipo_permisoLessThan(String value) {
            addCriterion("tipo_permiso <", value, "tipo_permiso");
            return (Criteria) this;
        }

        public Criteria andTipo_permisoLessThanOrEqualTo(String value) {
            addCriterion("tipo_permiso <=", value, "tipo_permiso");
            return (Criteria) this;
        }

        public Criteria andTipo_permisoLike(String value) {
            addCriterion("tipo_permiso like", value, "tipo_permiso");
            return (Criteria) this;
        }

        public Criteria andTipo_permisoNotLike(String value) {
            addCriterion("tipo_permiso not like", value, "tipo_permiso");
            return (Criteria) this;
        }

        public Criteria andTipo_permisoIn(List<String> values) {
            addCriterion("tipo_permiso in", values, "tipo_permiso");
            return (Criteria) this;
        }

        public Criteria andTipo_permisoNotIn(List<String> values) {
            addCriterion("tipo_permiso not in", values, "tipo_permiso");
            return (Criteria) this;
        }

        public Criteria andTipo_permisoBetween(String value1, String value2) {
            addCriterion("tipo_permiso between", value1, value2, "tipo_permiso");
            return (Criteria) this;
        }

        public Criteria andTipo_permisoNotBetween(String value1, String value2) {
            addCriterion("tipo_permiso not between", value1, value2, "tipo_permiso");
            return (Criteria) this;
        }

        public Criteria andMotivoIsNull() {
            addCriterion("motivo is null");
            return (Criteria) this;
        }

        public Criteria andMotivoIsNotNull() {
            addCriterion("motivo is not null");
            return (Criteria) this;
        }

        public Criteria andMotivoEqualTo(String value) {
            addCriterion("motivo =", value, "motivo");
            return (Criteria) this;
        }

        public Criteria andMotivoNotEqualTo(String value) {
            addCriterion("motivo <>", value, "motivo");
            return (Criteria) this;
        }

        public Criteria andMotivoGreaterThan(String value) {
            addCriterion("motivo >", value, "motivo");
            return (Criteria) this;
        }

        public Criteria andMotivoGreaterThanOrEqualTo(String value) {
            addCriterion("motivo >=", value, "motivo");
            return (Criteria) this;
        }

        public Criteria andMotivoLessThan(String value) {
            addCriterion("motivo <", value, "motivo");
            return (Criteria) this;
        }

        public Criteria andMotivoLessThanOrEqualTo(String value) {
            addCriterion("motivo <=", value, "motivo");
            return (Criteria) this;
        }

        public Criteria andMotivoLike(String value) {
            addCriterion("motivo like", value, "motivo");
            return (Criteria) this;
        }

        public Criteria andMotivoNotLike(String value) {
            addCriterion("motivo not like", value, "motivo");
            return (Criteria) this;
        }

        public Criteria andMotivoIn(List<String> values) {
            addCriterion("motivo in", values, "motivo");
            return (Criteria) this;
        }

        public Criteria andMotivoNotIn(List<String> values) {
            addCriterion("motivo not in", values, "motivo");
            return (Criteria) this;
        }

        public Criteria andMotivoBetween(String value1, String value2) {
            addCriterion("motivo between", value1, value2, "motivo");
            return (Criteria) this;
        }

        public Criteria andMotivoNotBetween(String value1, String value2) {
            addCriterion("motivo not between", value1, value2, "motivo");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table personas_eventos_permisos
     *
     * @mbggenerated do_not_delete_during_merge Mon Jun 13 11:29:50 BOT 2016
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table personas_eventos_permisos
     *
     * @mbggenerated Mon Jun 13 11:29:50 BOT 2016
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}