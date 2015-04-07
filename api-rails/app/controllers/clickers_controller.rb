class ClickersController < ApplicationController
  

    def index
    @user=User.find(params[:user_id])
    @room=@user.rooms.find(params[:room_id])
    @device =@room.devices.find(params[:device_device_id])
    @clickers= @device.clicker
    render json: @clickers if stale?(@clickers)

     end
 def show
    @user=User.find(params[:user_id])
    @room=@user.rooms.find(params[:room_id])
    @device =@room.devices.find(params[:device_device_id])
    @clickers= @device.clicker
    render json: @clickers if stale?(@clickers)
  end
	def create
    @user = User.find(params[:user_id])
    @room = @user.rooms.find(params[:room_id])
    @device = @room.devices.find(params[:device_device_id])
    @clicker = @device.create_clicker(clicker_params)
    @clicker.user_id = @room.user_id
    @clicker.room_id = @device.room_id
    @clicker.device_id = @device.device_id
    if @clicker.save
      render json: @clicker, status: :created
    else
      render json: @clicker.errors, status: :unprocessable_entity
    end
  end

 def update
    @user=User.find(params[:user_id])
    @room=@user.rooms.find(params[:room_id])
    @device= @room.devices.find(params[:device_device_id])
    @clicker =@device.clicker
            if@clicker.update(clicker_params)
             head :no_content
              else
               render json: @clicker.errors, status: :unprocessable_entity
            end    
end
    private
  # Never trust parameters from the scary internet, only allow the white list through.
  def clicker_params
    params.require(:clicker).permit(:command,:device_device_id,:user_id,:room_id)
  end
end
