package es.ieslavereda.repository;

import es.ieslavereda.model.ITWorker;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ITWorkerRepository {

    public ITWorker getITWorkerById(int id){
        ITWorker itworker = null;
        String query = "select id,nombre, apellidos,DNI,edad,email,experiencia,categoria from worker inner join itworker on id = idWorker where id = ?";
        try(Connection connection=MyDataSource.getMySQLDataSorce().getConnection();
            PreparedStatement ps = connection.prepareCall(query)
        ){
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                itworker = new ITWorker(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getString(6),rs.getInt(7), ITWorker.Categoria.getCategoriaFromString(rs.getString(8)));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return itworker;
    }


    public List<ITWorker> getAllITWorkers() {
        List<ITWorker> itWorkers = new ArrayList<>();
        String query = "{call getAllITWorkers()}";
        try(Connection connection=MyDataSource.getMySQLDataSorce().getConnection();
            CallableStatement cs = connection.prepareCall(query)){
            ResultSet rs = cs.executeQuery();
            while(rs.next()){
                itWorkers.add(new ITWorker(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getString(6),rs.getInt(7), ITWorker.Categoria.getCategoriaFromString(rs.getString(8))));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return itWorkers;
    }

    public ITWorker deleteITWorkerById(int id){
        ITWorker itWorker = getITWorkerById(id);
        if (itWorker!=null){
            String query = "delete from worker where id=?";
            try(Connection connection=MyDataSource.getMySQLDataSorce().getConnection();
                PreparedStatement ps = connection.prepareCall(query)
            ){
                ps.setInt(1,id);
                ps.executeUpdate();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        return itWorker;
    }

    public ITWorker updateITWorker(ITWorker itWorker){
        ITWorker workerAux = getITWorkerById(itWorker.getId());
        if (workerAux!=null){
            String query = "update worker set nombre = ?, apellidos = ?, DNI=?,edad=?,email=?,experiencia=? where id=?";
            String query2 = "update itworker set categoria = ? where idWorker=?";
            try(Connection connection=MyDataSource.getMySQLDataSorce().getConnection();
                PreparedStatement ps = connection.prepareCall(query);
                PreparedStatement ps2 = connection.prepareCall(query2);
            ){
                ps.setString(1, itWorker.getNombre());
                ps.setString(2,itWorker.getApellidos());
                ps.setString(3,itWorker.getDNI());
                ps.setInt(4,itWorker.getEdad());
                ps.setString(5,itWorker.getEmail());
                ps.setInt(6,itWorker.getExperiencia());
                ps.setInt(7,itWorker.getId());
                ps.executeUpdate();
                ps2.setString(1, itWorker.getRole());
                ps2.setInt(2,itWorker.getId());
                ps2.executeUpdate();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        return itWorker;
    }

    public ITWorker addITWorker(ITWorker itWorker){
        String query = "{?=call createITWorker(?,?,?,?,?,?,?)}";
        try(Connection connection=MyDataSource.getMySQLDataSorce().getConnection();
            CallableStatement cs = connection.prepareCall(query)){
            cs.registerOutParameter(1,Types.INTEGER);
            cs.setString(2, itWorker.getNombre());
            cs.setString(3, itWorker.getApellidos());
            cs.setString(4,itWorker.getDNI());
            cs.setInt(5,itWorker.getEdad());
            cs.setString(6,itWorker.getEmail());
            cs.setInt(7,itWorker.getExperiencia());
            cs.setString(8,itWorker.getRole());
            cs.execute();
            itWorker.setId(cs.getInt(1));
        } catch (SQLException e){
            e.printStackTrace();
        }
        return itWorker;
    }
}
