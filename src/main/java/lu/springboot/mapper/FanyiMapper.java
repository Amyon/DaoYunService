package lu.springboot.mapper;

import lu.springboot.entity.Fanyi;


public interface FanyiMapper {
    Fanyi findFirstLine();
    void deleteFanyiByID(int id);
    void insert(Fanyi fanyi);
    void updateByID(Fanyi fanyi);
}
