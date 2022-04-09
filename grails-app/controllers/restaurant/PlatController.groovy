package restaurant



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class PlatController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Plat.list(params), model:[platInstanceCount: Plat.count()]
    }

    def show(Plat platInstance) {
        respond platInstance
    }

    def create() {
        respond new Plat(params)
    }

    @Transactional
    def save(Plat platInstance) {
        if (platInstance == null) {
            notFound()
            return
        }

        if (platInstance.hasErrors()) {
            respond platInstance.errors, view:'create'
            return
        }

        platInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'plat.label', default: 'Plat'), platInstance.id])
                redirect platInstance
            }
            '*' { respond platInstance, [status: CREATED] }
        }
    }

    def edit(Plat platInstance) {
        respond platInstance
    }

    @Transactional
    def update(Plat platInstance) {
        if (platInstance == null) {
            notFound()
            return
        }

        if (platInstance.hasErrors()) {
            respond platInstance.errors, view:'edit'
            return
        }

        platInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Plat.label', default: 'Plat'), platInstance.id])
                redirect platInstance
            }
            '*'{ respond platInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Plat platInstance) {

        if (platInstance == null) {
            notFound()
            return
        }

        platInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Plat.label', default: 'Plat'), platInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'plat.label', default: 'Plat'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
