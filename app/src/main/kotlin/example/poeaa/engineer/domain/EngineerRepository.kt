package example.poeaa.engineer.domain

interface EngineerRepository {
    fun findByIds(ids: Collection<EngineerId>): Set<Engineer>
}
