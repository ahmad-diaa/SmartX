class NotesController < ApplicationController

	def index
	end

	def show
	end

	def new
	end

	def destroy
	end

	 private
  # Never trust parameters from the scary internet, only allow the white list through.
  def note_params
    params.require(:note).permit(:body,:device_id,:user_id,:room_id)
  end

end