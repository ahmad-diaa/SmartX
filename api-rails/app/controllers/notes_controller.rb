class NotesController < ApplicationController

	#It takes the user ID, room ID and device ID from the route 
	#returns all notes of that specific device
	def index
		@user=User.find(params[:user_id])
    	@room=@user.rooms.find(params[:room_id])
    	@device= @room.devices.find(params[:device_device_id])
    	@notes = @device.notes.all
    	render json: @notes if stale?(etag: @notes.all, last_modified: @notes.maximum(:updated_at))
  	end

	#It takes the user ID, room ID, device ID and note ID from the route 
	#returns that specific note with all its parameter
	def show
		@user=User.find(params[:user_id])
    	@room=@user.rooms.find(params[:room_id])
   		@device= @room.devices.find(params[:device_device_id])
   		@note = @device.notes.find(params[:id])
    	render json: @note if stale?(@note)
	end

	def create
		@user=User.find(params[:user_id])
		@room = @user.rooms.find(params[:room_id])
		@device = @room.devices.find(params[:device_device_id])
   		@note = @device.notes.create(note_params)
   		@note.user_id= @device.user_id
   		@note.room_id = @device.room_id
    	if @note.save
      		render json: @note, status: :created
    	else
      		render json: @note.errors, status: :unprocessable_entity
    	end
	end

	def destroy
		@user=User.find(params[:user_id])
    	@room=@user.rooms.find(params[:room_id])
    	@device= @room.devices.find(params[:device_device_id])
    	@note = @device.notes.find(params[:id])
    	@note.destroy
    	head :no_content
	end

	 private
  # Never trust parameters from the scary internet, only allow the white list through.
  def note_params
    params.require(:note).permit(:body,:device_device_id,:user_id,:room_id,:id)
  end

end